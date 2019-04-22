package app.pp.service.impl;

import app.pp.entity.Group;
import app.pp.entity.SaleSlip;
import app.pp.entity.Saleman;
import app.pp.entity.SysUserEntity;
import app.pp.exceptions.GlobleException;
import app.pp.mapper.SaleSlipMapper;
import app.pp.mapper.SalemanMapper;
import app.pp.mapper.SysUserDao;
import app.pp.service.GroupService;
import app.pp.service.SaleSlipService;
import app.pp.service.SalemanService;
import app.pp.utils.OrderCodeUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;
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
    public void save(SaleSlip slip) {
        //系统自动分配 当前账号车行  对应的  保单号
        Group group =  groupService.getCurrentGroup();
        if(3 != group.getType()){
            throw new GlobleException("当前账号不是车行账号，不能添加销售单");
        }



        slip.setSalenum(OrderCodeUtils.generateCode());
        //保险开始日期
        slip.setPstarttime(slip.getInstalltime());
        //保险结束日期
        Calendar cal = Calendar.getInstance();
        cal.setTime(slip.getInstalltime());
        cal.add(Calendar.YEAR, 1);//增加一年
        slip.setPendtime(cal.getTime());
        saleSlipMapper.insert(slip);
    }

    public void update(SaleSlip slip) {
        saleSlipMapper.update(slip);
    }

    public void delete(Integer id) {

    }

    public List<SaleSlip> selectall(Map<String,Object> param) {
        List<Group> groups = groupService.selectall();
        param.put("groups",groups);
        return saleSlipMapper.selectAll(param);
    }
}
