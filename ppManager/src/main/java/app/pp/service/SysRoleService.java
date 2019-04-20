package app.pp.service;

import app.pp.entity.SysRoleEntity;

import java.util.List;
import java.util.Map;


/**
 * 角色
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:42:52
 */
public interface SysRoleService{

	void save(SysRoleEntity role);

	void update(SysRoleEntity role);

	void deleteBatch(Long[] roleIds);

	
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);

	List<SysRoleEntity> selectByMap(Map<String, Object> map);

	SysRoleEntity selectById(Long roleId);

	List<SysRoleEntity> selectWaibuByMap(Map<String, Object> map);
}
