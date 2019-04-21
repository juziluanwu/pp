package app.pp.service.impl;

import app.pp.entity.Group;
import app.pp.entity.SysUserEntity;
import app.pp.exceptions.GlobleException;
import app.pp.mapper.GroupMapper;
import app.pp.mapper.SysUserDao;
import app.pp.service.GroupService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 账号分组
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private SysUserDao sysUserDao;

    public void save(Group group) {
        groupMapper.insert(group);
    }

    public void update(Group group) {
        groupMapper.update(group);
    }

    public void delete(Integer id) {
        //分组下有子分组
        int childnum = groupMapper.selectCountByPid(id);
        if (childnum > 0) {
            throw new GlobleException("该分组下有子分组，请删除子分组");
        }
        //分组下有账号不能删除
        int usernum = sysUserDao.selectCountByGroupid(id);
        if (usernum > 0) {
            throw new GlobleException("该分组下有正常使用的账号，不能删除");
        }
        Group group = new Group();
        group.setId(id);
        group.setIsdel(1);
        group.setUpdatedtime(new Date());
        SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        group.setUpdator(user.getUserId());
        groupMapper.update(group);
    }

    public List<Group> selectall() {
        List<Group> list = new ArrayList<>();
        SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        Group group = groupMapper.selectById(user.getGroupid());
        if(group != null){
            if(0 == group.getType()){
                //管理员权限的分组   可以查看所有分组
                list = groupMapper.selectAll();

            }else{
                //其他权限分组 只能查看自己及子集
                list.add(group);
                List<Group> child = new ArrayList<>();
                child.add(group);
                getChild(list,child);
            }
        }

        return list;
    }

    public void getChild( List<Group> list ,List<Group> child){
        if(child != null && !child.isEmpty()) {
            for (Group group : child) {
                List<Group> childlist = groupMapper.selectByPid(group.getId());
                if (childlist != null && !childlist.isEmpty()) {
                    list.addAll(childlist);
                    getChild(list, childlist);
                }
            }
        }
    }

}
