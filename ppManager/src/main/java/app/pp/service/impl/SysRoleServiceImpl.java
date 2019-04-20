package app.pp.service.impl;


import app.pp.entity.SysRoleEntity;
import app.pp.exceptions.GlobleException;
import app.pp.mapper.SysRoleDao;
import app.pp.service.SysRoleMenuService;
import app.pp.service.SysRoleService;
import app.pp.service.SysUserRoleService;
import app.pp.service.SysUserService;
import app.pp.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 角色
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:45:12
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
	private SysRoleDao sysRoleDao;

	@Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysRoleEntity role) {
        role.setCreateTime(new Date());
		sysRoleDao.insert(role);
     /*   //检查权限是否越权
        checkPrems(role);*/

        //保存角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleEntity role) {
		sysRoleDao.updateById(role);

       /* //检查权限是否越权
        checkPrems(role);*/

        //更新角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] roleIds) {
        //删除角色
		sysRoleDao.deleteBatchIds(Arrays.asList(roleIds));

        //删除角色与菜单关联
        sysRoleMenuService.deleteBatch(roleIds);

        //删除角色与用户关联
        sysUserRoleService.deleteBatch(roleIds);
    }


    @Override
	public List<Long> queryRoleIdList(Long createUserId) {
		return sysRoleDao.queryRoleIdList(createUserId);
	}

	@Override
	public List<SysRoleEntity> selectByMap(Map<String, Object> map) {
		return sysRoleDao.selectByMap(map);
	}

	@Override
	public SysRoleEntity selectById(Long roleId) {
		return sysRoleDao.selectById(roleId);
	}

	/**
	 * 检查权限是否越权
	 */
	private void checkPrems(SysRoleEntity role){
		//如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
		if(role.getCreateUserId() == Constant.SUPER_ADMIN){
			return ;
		}
		
		//查询用户所拥有的菜单列表
		List<Long> menuIdList = sysUserService.queryAllMenuId(role.getCreateUserId());
		
		//判断是否越权
		if(!menuIdList.containsAll(role.getMenuIdList())){
			throw new GlobleException("新增角色的权限，已超出你的权限范围");
		}
	}

	public List<SysRoleEntity> selectWaibuByMap(Map<String, Object> map){
		return sysRoleDao.selectWaibuByMap(map);
	}
}
