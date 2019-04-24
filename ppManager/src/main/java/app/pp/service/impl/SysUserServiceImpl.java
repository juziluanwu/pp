package app.pp.service.impl;

import app.pp.common.Result;
import app.pp.entity.Group;
import app.pp.entity.SysUserEntity;
import app.pp.enums.ErrorEnum;
import app.pp.mapper.GroupMapper;
import app.pp.mapper.SysUserDao;
import app.pp.service.GroupService;
import app.pp.service.SysRoleService;
import app.pp.service.SysUserRoleService;
import app.pp.service.SysUserService;
import app.pp.utils.Constant;
import app.pp.utils.ResultUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 系统用户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:46:09
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private GroupService groupService;


    @Override
    public List<String> queryAllPerms(Long userId) {
        return sysUserDao.queryAllPerms(userId);
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return sysUserDao.queryAllMenuId(userId);
    }

    @Override
    public SysUserEntity queryByUserName(String username) {
        return sysUserDao.queryByUserName(username);
    }

    @Override
    @Transactional
    public Result save(SysUserEntity user) {

        SysUserEntity old = sysUserDao.selectByUsername(user.getUsername());
        if (old != null) {
            return ResultUtils.fail("用户名已存在");
        }

        SysUserEntity s = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        List<Long> rolelist = user.getRoleIdList();
        user.setCreateTime(new Date());
        user.setPassword(user.getPassword());
        Integer i = sysUserDao.insert(user);
		/*//检查角色是否越权
		checkRole(user);*/

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
        return ResultUtils.result(ErrorEnum.SUCCESS, "创建用户成功");
    }

    @Override
    @Transactional
    public Result update(SysUserEntity user) {
        SysUserEntity old = sysUserDao.selectByUsername(user.getUsername());
        if (old != null) {
            if (old.getUserId().longValue() != user.getUserId().longValue()) {
                return ResultUtils.fail("用户名已存在");
            }
        }
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            SysUserEntity sysUserEntity = sysUserDao.selectById(user.getUserId());
            if (!user.getPassword().equals(sysUserEntity.getPassword())) {
                user.setPassword(user.getPassword());
            }
        }
        sysUserDao.updateById(user);

		/*//检查角色是否越权
		checkRole(user);*/

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());

        return ResultUtils.result(ErrorEnum.SUCCESS, "修改成功");
    }

    @Override
    @Transactional
    public Integer deleteBatch(Long[] userId) {
        return sysUserDao.deleteBatchIds(Arrays.asList(userId));
    }


    @Override
    public boolean updatePassword(Long userId, String password, String newPassword) {
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setPassword(newPassword);
        userEntity.setUserId(userId);
        userEntity.setPassword(password);
        int i = sysUserDao.updatePassword(userId, password, newPassword);
        if (i > 0) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public SysUserEntity selectById(Long userId) {
        SysUserEntity u = sysUserDao.selectById(userId);
        if (u != null) {
            String username = u.getUsername();
            String[] a = username.split("@");
            u.setUsername(a[0]);
        }
        return u;
    }

    @Override
    public List<SysUserEntity> selectByMap(Map params) {
        SysUserEntity s = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        Group group =groupMapper.selectById(s.getGroupid());
        if(group != null){
            if(0 == group.getType()){
                //管理员分组

            }else{
                //非管理员分组
                List<Group> groups = groupService.selectall();
                params.put("groups",groups);
            }
        }
        return sysUserDao.selectByMap(params);
    }

    /**
     * 检查角色是否越权
     */
    private void checkRole(SysUserEntity user) {
        if (user.getRoleIdList() == null || user.getRoleIdList().size() == 0) {
            return;
        }
        //如果不是超级管理员，则需要判断用户的角色是否自己创建
        if (user.getCreateUserId() == Constant.SUPER_ADMIN) {
            return;
        }
		
		/*//查询用户创建的角色列表
		List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());

		//判断是否越权
		if(!roleIdList.containsAll(user.getRoleIdList())){
			throw new GlobleException("新增用户所选角色，不是本人创建");
		}*/
    }

    public SysUserEntity getCurrentUser(){
        return  (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    }
}
