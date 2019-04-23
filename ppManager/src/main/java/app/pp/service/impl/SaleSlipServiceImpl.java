package app.pp.service.impl;

import app.pp.entity.*;
import app.pp.exceptions.GlobleException;
import app.pp.mapper.PolicyMapper;
import app.pp.mapper.PrintRecordMapper;
import app.pp.mapper.SaleSlipMapper;
import app.pp.mapper.SaleVoidRecordMapper;
import app.pp.service.GroupService;
import app.pp.service.SaleSlipService;
import app.pp.utils.OrderCodeUtils;
import app.pp.vo.RenewalVO;
import app.pp.vo.SaleSlipDelVO;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


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
    public void save(SaleSlip slip) {
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
        slip.setShop4s(group.getName());
        slip.setSalenum(OrderCodeUtils.generateCode());
        //保险开始日期
        slip.setPstarttime(slip.getInstalltime());
        //保险结束日期
        Calendar cal = Calendar.getInstance();
        cal.setTime(slip.getInstalltime());
        cal.add(Calendar.YEAR, 1);//增加一年
        slip.setPendtime(cal.getTime());

        //最高赔付金额 = 新车购入价格
        slip.setCompensation(slip.getCarprice());
        saleSlipMapper.insert(slip);
        //将保单变更为 关联状态

    }

    public void update(SaleSlip slip) {
        SaleSlip oldslip = saleSlipMapper.findById(slip.getId());
        if (oldslip != null) {
            if (1 == oldslip.getPrintstate()) {
                //判断设备号是否一致  不一致废弃老的设备号
                if (oldslip.getDeviceid().equals(slip.getDeviceid())) {

                }
                //状态变成为 未打印
                //slip.setPrintstate(1);
                SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
                slip.setUpdator(user.getUserId());
                slip.setUpdatedtime(new Date());
                saleSlipMapper.update(slip);
            } else {
                throw new GlobleException("已打印的销售单不能编辑");
            }
        }


    }

    @Transactional
    public void delete(SaleSlipDelVO vo) {
        SaleSlip ss = saleSlipMapper.selectById(vo.getId());
        if (ss != null) {
            SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
            //将销售单变成为废弃状态
            SaleSlip delss = new SaleSlip();
            delss.setId(ss.getId());
            delss.setDelstate(1);
            delss.setUpdatedtime(new Date());
            delss.setUpdator(user.getUserId());
            saleSlipMapper.update(delss);

            //创建销售单废弃记录
            SaleVoidRecord svr = new SaleVoidRecord();
            svr.setReason(vo.getReason());
            svr.setRemark(vo.getReamrk());
            if (1 == ss.getPrintstate()) {
                BeanUtils.copyProperties(ss, svr);
            } else if (2 == ss.getPrintstate()) {
                if (1 == vo.getReason()) {
                    //退保   可以编辑所有？
                    svr.setDeviceid(ss.getDeviceid());
                    svr.setPolicydate(ss.getPolicydate());
                    svr.setPstarttime(ss.getPstarttime());
                    svr.setPendtime(ss.getPendtime());
                    svr.setBuycartype(ss.getBuycartype());
                    svr.setCarprice(ss.getCarprice());
                    svr.setCompensation(ss.getCompensation());
                    svr.setFirstbeneficiary(ss.getFirstbeneficiary());

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
            }

            svr.setDelman(user.getUserId());
            svr.setDeltime(new Date());
            saleVoidRecordMapper.insert(svr);
        }
    }

    public List<SaleSlip> selectall(Map<String, Object> param) {
        List<Group> groups = groupService.selectall();
        param.put("groups", groups);
        return saleSlipMapper.selectAll(param);
    }

    public SaleSlip info(Integer id) {
        return saleSlipMapper.selectById(id);
    }

    @Transactional
    public void renewal(RenewalVO vo) {
        SaleSlip ss = saleSlipMapper.selectById(vo.getSaleslipid());
        if (ss != null) {
            SaleSlip newss = new SaleSlip();
            newss.setId(ss.getId());
            //打印状态变更为 未打印
            newss.setPrintstate(1);
            //变更 保单生效时间为  以前的结束时间加1天
            Calendar cal = Calendar.getInstance();
            cal.setTime(ss.getPendtime());
            cal.add(Calendar.DAY_OF_MONTH, 1);//增加一年
            newss.setPstarttime(cal.getTime());
            saleSlipMapper.update(newss);
        } else {
            throw new GlobleException("保单不存在");
        }
    }

    public int getRenewalLimit(Integer id){
        int num = 0;
        SaleSlip ss = saleSlipMapper.findById(id);
        if(ss != null){
            int plimit = printRecordMapper.selectSumdateBySaleslipid(id);
            if(plimit >= ss.getPolicydate()){
                //已打印累计期限 >=销售单期限
                throw new GlobleException("已超出销售单期限，不能续期");
            }else{
                num = ss.getPolicydate()-plimit;
            }
        }
        return num;
    }
}
