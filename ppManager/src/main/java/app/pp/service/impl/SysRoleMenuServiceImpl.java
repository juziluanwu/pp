package app.pp.service.impl;

import app.pp.entity.SysRoleMenuEntity;
import app.pp.mapper.SysRoleMenuDao;
import app.pp.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 角色与菜单对应关系
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:44:35
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl  implements SysRoleMenuService {

	@Autowired
	SysRoleMenuDao sysRoleMenuDao;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
		//先删除角色与菜单关系
		deleteBatch(new Long[]{roleId});

		if(menuIdList.size() == 0){
			return ;
		}

		//保存角色与菜单关系
		List<SysRoleMenuEntity> list = new ArrayList<>(menuIdList.size());
		for(Long menuId : menuIdList){
			SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
			sysRoleMenuEntity.setMenuId(menuId);
			sysRoleMenuEntity.setRoleId(roleId);

			list.add(sysRoleMenuEntity);
		}
		sysRoleMenuDao.insertBatch(list);
	}

	@Override
	public List<Long> queryMenuIdList(Long roleId) {
		return sysRoleMenuDao.queryMenuIdList(roleId);
	}

	@Override
	public int deleteBatch(Long[] roleIds){
		return sysRoleMenuDao.deleteBatch(roleIds);
	}

	@Override
	public void deleteByMap(Map map) {
		sysRoleMenuDao.deleteByMap(map);
	}

	@Override
	public List<Long> queryMenuIdListType2(Long roleId) {
		return sysRoleMenuDao.queryMenuIdListType2(roleId);
	}

}
