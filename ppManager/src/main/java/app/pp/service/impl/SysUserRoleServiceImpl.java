package app.pp.service.impl;


import app.pp.entity.SysUserRoleEntity;
import app.pp.mapper.SysUserRoleDao;
import app.pp.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用户与角色对应关系
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:45:48
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl  implements SysUserRoleService {

	@Autowired
	SysUserRoleDao sysUserRoleDao;
	@Override
	public void saveOrUpdate(Long userId, List<Long> roleIdList) {
		Map param = new HashMap();
		param.put("user_id",userId);
		//先删除用户与角色关系
		sysUserRoleDao.deleteByMap(param);

		if(roleIdList == null || roleIdList.size() == 0){
			return ;
		}

		//保存用户与角色关系
		List<SysUserRoleEntity> list = new ArrayList<>(roleIdList.size());
		for(Long roleId : roleIdList){
			SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
			sysUserRoleEntity.setUserId(userId);
			sysUserRoleEntity.setRoleId(roleId);

			list.add(sysUserRoleEntity);
		}
		sysUserRoleDao.insertBatch(list);
	}

	@Override
	public List<Long> queryRoleIdList(Long userId) {
		return sysUserRoleDao.queryRoleIdList(userId);
	}

	@Override
	public int deleteBatch(Long[] roleIds){
		return sysUserRoleDao.deleteBatch(roleIds);
	}
}
