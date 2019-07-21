package app.pp.service.impl;

import app.pp.entity.*;
import app.pp.exceptions.GlobleException;
import app.pp.mapper.*;
import app.pp.service.DeviceService;
import app.pp.service.GroupService;
import app.pp.service.SaleSlipService;
import app.pp.service.SysUserService;
import app.pp.utils.GlobleUtils;
import app.pp.utils.OrderCodeUtils;
import app.pp.vo.RenewalVO;
import app.pp.vo.SaleSlipDelVO;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 销售单
 */
@Service
public class SaleSlipServiceImpl implements SaleSlipService {

    @Autowired
    private SaleSlipMapper saleSlipMapper;
    @Autowired
    private GroupService groupService;
    @Autowired
    private PolicyMapper policyMapper;
    @Autowired
    private SaleVoidRecordMapper saleVoidRecordMapper;
    @Autowired
    private PrintRecordMapper printRecordMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SlipRenewalMapper slipRenewalMapper;
    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private DeviceService deviceService;

    @Transactional
    public void save(SaleSlip slip) {
        Integer deviceid = deviceService.testDevice(slip.getDevicenum());
        slip.setDeviceid(deviceid);
        //系统自动分配 当前账号车行  对应的  保单号
        Group group = groupService.getCurrentGroup();
        if (3 != group.getType()) {
            throw new GlobleException("当前账号不是车行账号，不能添加销售单");
        }
        Policy policy = policyMapper.selectByGroupid(group.getId());
        if (policy == null) {
            throw new GlobleException("没有可用的保单号，请先添加保单");
        }
        slip.setPolicyid(policy.getId());
        slip.setGroupid(group.getId());
        slip.setShop4s(group.getName());
        slip.setSalenum(OrderCodeUtils.generateCode());
        //保险开始日期
        slip.setPstarttime(slip.getInstalltime());
        //保险结束日期
        Calendar cal = Calendar.getInstance();
        cal.setTime(slip.getInstalltime());
        cal.add(Calendar.YEAR, slip.getPolicydate());//增加一年
        cal.add(Calendar.DAY_OF_MONTH, -1);//减1天
        slip.setPendtime(cal.getTime());

        //最高赔付金额 = 新车购入价格
        slip.setCompensation(slip.getCarprice());
        slip.setDelstate(1);
        saleSlipMapper.insert(slip);
        //将保单变更为 关联状态
        policy.setState(2);
        policyMapper.updateByPrimaryKeySelective(policy);
        //将设备变更为 关联状态
        Device d = new Device();
        d.setId(slip.getDeviceid());
        d.setState(2);
        deviceMapper.updateByPrimaryKeySelective(d);
    }

    /**
     * 编辑
     *
     * @param slip
     */
    @Transactional
    public void update(SaleSlip slip) {
        SaleSlip oldslip = saleSlipMapper.findById(slip.getId());
        if (oldslip != null) {
            if (1 == oldslip.getPrintstate()) {
                Device device = deviceMapper.selectDeviceExsit(slip.getDevicenum());
                if (device == null) {
                    throw new GlobleException("设备号不存在");
                }
                slip.setDeviceid(device.getId());
                if (!oldslip.getDeviceid().equals(slip.getId())) {
                    //编辑后的设备号 和  编辑前的设备号 不一致     说明 修改了设备号
                    if (2 == device.getState()) {
                        throw new GlobleException("设备号已被别的销售单绑定");
                    } else if (3 == device.getState()) {
                        throw new GlobleException("设备号已被作废");
                    }
                    //判断设备号是否一致  不一致废弃老的设备号
                    Device d = new Device();
                    d.setId(slip.getDeviceid());
                    d.setState(1);
                    deviceMapper.updateByPrimaryKeySelective(d);

                }
                if (oldslip.getPolicyid() == null) {
                    Group group = groupService.getCurrentGroup();
                    Policy policy = policyMapper.selectByGroupid(group.getId());
                    if (policy == null) {
                        throw new GlobleException("没有可用的保单号，请先添加保单");
                    }
                    slip.setPolicyid(policy.getId());
                    //将保单修改为已关联状态
                    Policy p = new Policy();
                    p.setId(policy.getId());
                    p.setState(2);
                    policyMapper.updateByPrimaryKeySelective(p);
                }
                //状态变成为 未打印
                slip.setUpdator(sysUserService.getCurrentUser().getUserId());
                slip.setUpdatedtime(new Date());
                saleSlipMapper.update(slip);
                //将设备变更为 关联状态
                Device d = new Device();
                d.setId(slip.getDeviceid());
                d.setState(2);
                deviceMapper.updateByPrimaryKeySelective(d);
            } else {
                throw new GlobleException("已打印的销售单不能编辑");
            }
        }
    }

    /**
     * 销售单作废
     *
     * @param vo
     */
    @Transactional
    public void delete(SaleSlipDelVO vo) {
        SaleSlip ss = saleSlipMapper.selectById(vo.getId());
        if (ss != null) {
            SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
            //将销售单变成为废弃状态
            SaleSlip delss = new SaleSlip();
            delss.setId(ss.getId());
            //变更为未打印状态
            delss.setPrintstate(1);

            if (1 == vo.getReason()) {
                //退保
                //将销售单设置为全部可编辑状态
                delss.setDelstate(1);
                // 将 保单和销售单取消关联      整个保单信息就无效了可以修改成别人的了
                saleSlipMapper.setPolicyidNull(ss.getId());
                //将保单变更为未关联状态
                Policy p = new Policy();
                p.setId(ss.getPolicyid());
                p.setState(1);
                policyMapper.updateByPrimaryKeySelective(p);
                //清空打印记录
                printRecordMapper.deleteBySaleslipid(ss.getId());
            } else {
                //将销售单设置为部分可编辑状态
                delss.setDelstate(2);
            }
            delss.setUpdatedtime(new Date());
            delss.setUpdator(user.getUserId());
            saleSlipMapper.update(delss);

            //创建销售单废弃记录
            SaleVoidRecord svr = new SaleVoidRecord();
            svr.setReason(vo.getReason());
            svr.setRemark(vo.getRemark());
            svr.setGroupid(ss.getGroupid());
            svr.setDevicenum(ss.getDevicenum());
            svr.setPnum(ss.getPnum());
            if (1 == vo.getReason()) {
                //退保
                BeanUtils.copyProperties(ss, svr);
               /* svr.setDeviceid(ss.getDeviceid());
                svr.setPolicydate(ss.getPolicydate());
                svr.setPstarttime(ss.getPstarttime());
                svr.setPendtime(ss.getPendtime());
                svr.setBuycartype(ss.getBuycartype());
                svr.setCarprice(ss.getCarprice());
                svr.setCompensation(ss.getCompensation());
                svr.setFirstbeneficiary(ss.getFirstbeneficiary());*/

            } else {
                //剩下所有原因
                svr.setCustomername(ss.getCustomername());
                svr.setCertificatenum(ss.getCertificatenum());
                svr.setAddress(ss.getAddress());
                svr.setPhone(ss.getPhone());
                svr.setCarbrandid(ss.getCarbrandid());
                svr.setCarsysid(ss.getCarsysid());
                svr.setEnginenum(ss.getEnginenum());
                svr.setFrame(ss.getFrame());
            }
            svr.setDelman(user.getName());
            svr.setDeltime(new Date());
            saleVoidRecordMapper.insert(svr);
        }
    }

    /**
     * 销售单列表
     *
     * @param param
     * @return
     */
    public List<SaleSlip> selectallpage(Map<String, Object> param) {
        Group group = groupService.getCurrentGroup();
        if (group != null) {
            List<Group> groups = groupService.selectall();
            if (4 == group.getType() || 5 == group.getType()) {
                param.put("firstbeneficiarys", groups);
            } else {
                param.put("groups", groups);
            }
        }
        PageHelper.startPage(null == param.get("page") ? 1 : (int) param.get("page"), GlobleUtils.DEFAULT_PAGE_SIZE);
        return saleSlipMapper.selectAll(param);
    }

    public List<SaleSlip> selectall(Map<String, Object> param) {
        Group group = groupService.getCurrentGroup();
        if (group != null) {
            List<Group> groups = groupService.selectall();
            if (4 == group.getType() || 5 == group.getType()) {
                param.put("firstbeneficiarys", groups);
            } else {
                param.put("groups", groups);
            }
        }
        return saleSlipMapper.selectAll(param);
    }

    /**
     * 获取销售单信息
     *
     * @param id
     * @return
     */
    public SaleSlip info(Integer id) {
        return saleSlipMapper.selectById(id);
    }

    /**
     * 保单续费
     *
     * @param vo
     */
    @Transactional
    public void renewal(RenewalVO vo) {
        SaleSlip ss = saleSlipMapper.selectById(vo.getSaleslipid());
        if (ss != null) {
            SaleSlip newss = new SaleSlip();
            newss.setId(ss.getId());
            //打印状态变更为 未打印状态
            newss.setPrintstate(1);
            //变更 保单生效时间为  以前的开始日期+打印年限
            //查询打印年限
            int i = printRecordMapper.selectSumdateBySaleslipid(vo.getSaleslipid());
            Calendar cal = Calendar.getInstance();
            cal.setTime(ss.getPstarttime());
            cal.add(Calendar.YEAR, i);
            newss.setPstarttime(cal.getTime());
            //销售单设置为不可编辑状态
            newss.setDelstate(3);
            saleSlipMapper.update(newss);

            //插入续费记录
            SlipRenewal sr = new SlipRenewal();
            sr.setSaleslipid(ss.getId());
            sr.setDate(vo.getDate());
            sr.setOpinion(vo.getOpinion());
            sr.setCreator(sysUserService.getCurrentUser().getUserId());
            sr.setCreatedtime(new Date());
            slipRenewalMapper.insert(sr);
        } else {
            throw new GlobleException("保单不存在");
        }
    }

    public Map<String, Object> getRenewalInfo(Integer id) {
        Map<String, Object> result = new HashMap<>();
        SaleSlip ss = saleSlipMapper.selectById(id);
        if (ss != null) {
            result.put("customername", ss.getCustomername());
            result.put("carnum", ss.getCarnum());
            result.put("pstarttime", ss.getPstarttime());
            result.put("pendtime", ss.getPendtime());
            result.put("pnum", ss.getPnum());
            int i = printRecordMapper.selectSumdateBySaleslipid(id);
            result.put("renewallimit", ss.getPolicydate() - i);
        }
        return result;
    }
}

