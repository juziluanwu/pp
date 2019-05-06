package app.pp.service.impl;

import app.pp.entity.Group;
import app.pp.entity.Saleman;
import app.pp.entity.SysUserEntity;
import app.pp.mapper.SalemanMapper;
import app.pp.service.GroupService;
import app.pp.service.SalemanService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 账号分组
 */
@Service
public class SalemanServiceImpl implements SalemanService {

    @Autowired
    private SalemanMapper salemanMapper;

    @Autowired
    private GroupService groupService;

    public void save(Saleman saleman) {
        salemanMapper.insert(saleman);
    }

    public void update(Saleman saleman) {
        salemanMapper.update(saleman);
    }

    public void delete(Integer id) {

        Saleman saleman = new Saleman();
        saleman.setId(id);
        saleman.setIsdel(1);
        saleman.setUpdatedtime(new Date());
        SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        saleman.setUpdator(user.getUserId());
        salemanMapper.update(saleman);
    }

    public List<Saleman> selectall(Map<String, Object> param) {
        List<Group> list = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            param.put("groups", groupService.getAllCarGroup());
        }
        return salemanMapper.selectAll(param);
    }
}
