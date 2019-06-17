package app.pp.service.impl;

import app.pp.entity.Group;
import app.pp.entity.GroupModel;
import app.pp.entity.SysUserEntity;
import app.pp.exceptions.GlobleException;
import app.pp.mapper.GroupMapper;
import app.pp.mapper.GroupModelMapper;
import app.pp.mapper.SysUserDao;
import app.pp.service.GroupService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private GroupModelMapper groupModelMapper;

    @Transactional
    public void save(Group group) {
        groupMapper.insert(group);
        if (group.getGroupModelList() != null && !group.getGroupModelList().isEmpty()) {
            for (GroupModel gm : group.getGroupModelList()) {
                gm.setGid(group.getId());
                groupModelMapper.insert(gm);
            }
        }
    }

    @Transactional
    public void update(Group group) {
        Group oldgroup = groupMapper.selectById(group.getId());
        Group pg = groupMapper.selectById(group.getPid());
        if (!oldgroup.getPid().equals(group.getPid())) {

            if(2 == group.getType()){
                if(!(1 == pg.getType() || 0 == group.getPid())){
                    throw new GlobleException("无法迁移集团到此分组下");
                }
            } else if (3 == group.getType()) {
                //车行转移分组
                if (4 == pg.getType() || 5 == pg.getType() || 3 == pg.getType() || (3 == pg.getType() && 0 != group.getPid())) {
                    throw new GlobleException("无法迁移车行到此分组下");
                }
            } else if (0 == group.getType()) {
                //管理员
                if (0 != pg.getType()) {
                    throw new GlobleException("无法迁移到此分组下");
                }
            } else if (5 == group.getType()) {
                if (!(4 == pg.getType() || 0 == group.getPid())) {
                    throw new GlobleException("金融公司无法迁移到此分组下");
                }
            }else if(1 == group.getType() || 4 == group.getType()){
                throw new GlobleException("改分组无法迁移");
            }
        }
        groupMapper.update(group);
        groupModelMapper.deleteByGid(group.getId());
        if (group.getGroupModelList() != null && !group.getGroupModelList().isEmpty()) {
            for (GroupModel gm : group.getGroupModelList()) {
                gm.setGid(group.getId());
                groupModelMapper.insert(gm);
            }
        }
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
        Group group = getCurrentGroup();
        if (group != null) {
            if (0 == group.getType()) {
                //管理员权限的分组   可以查看所有分组
                list = groupMapper.selectAll();

            } else {
                //其他权限分组 只能查看自己及子集
                list.add(group);
                List<Group> child = new ArrayList<>();
                child.add(group);
                getChild(list, child);
            }
        }

        return list;
    }

    public void getChild(List<Group> list, List<Group> child) {
        if (child != null && !child.isEmpty()) {
            for (Group group : child) {
                List<Group> childlist = groupMapper.selectByPid(group.getId());
                if (childlist != null && !childlist.isEmpty()) {
                    list.addAll(childlist);
                    getChild(list, childlist);
                }
            }
        }
        // return list;
    }


    public Group getCurrentGroup() {
        SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        Group group = groupMapper.selectById(user.getGroupid());
        if (group == null) {
            throw new GlobleException("当前账号没有分组");
        }
        return group;
    }

    public Group info(Integer id) {
        Group group = groupMapper.selectById(id);
        if (group != null) {
            group.setGroupModelList(groupModelMapper.selectByGid(id));
        }
        return group;
    }

    public List<Group> firstbeneficiarylist() {
        return groupMapper.selectByType(5);
    }


    public List<Group> selecGroup() {
        List<Group> list = new ArrayList<>();
        Group group = getCurrentGroup();
        if (group != null) {
            if (0 == group.getType()) {
                //管理员权限的分组   可以查看所有分组
                list = groupMapper.selecGroup();

            } else {
                //其他权限分组 只能查看自己及子集
                if (group.getType() == 3) {
                    list.add(group);
                }
                List<Group> child = new ArrayList<>();
                child.add(group);
                getChild2(list, child);
            }
        }

        return list;
    }

    public List<Group> getChild2(List<Group> list, List<Group> child) {
        if (child != null && !child.isEmpty()) {
            for (Group group : child) {
                List<Group> childlist = groupMapper.selectByPidGroup(group.getId());
                if (childlist != null && !childlist.isEmpty()) {
                    list.addAll(childlist);
                    getChild(list, childlist);
                }
            }
        }
        return list;
    }

    public List<Group> getAllCarGroup() {
        List<Group> list = new ArrayList<>();
        Group group = getCurrentGroup();
        List<Group> child = new ArrayList<>();
        child.add(group);
        getChild2(list, child);
        return list;
    }

    public List<Group> selectAll() {
        return groupMapper.selectAll();
    }
}
