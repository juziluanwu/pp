/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package app.pp.service.impl;



import app.pp.entity.SysMenuEntity;
import app.pp.mapper.SysMenuDao;
import app.pp.service.SysMenuService;
import app.pp.service.SysRoleMenuService;
import app.pp.service.SysUserService;
import app.pp.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("sysMenuService")
public class SysMenuServiceImpl  implements SysMenuService {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private  SysMenuDao sysMenuDao;


	@Override
	public List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
		List<SysMenuEntity> menuList = queryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}

		List<SysMenuEntity> userMenuList = new ArrayList<>();
		for(SysMenuEntity menu : menuList){
			if(menuIdList.contains(menu.getMenuId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	@Override
	public List<SysMenuEntity> queryListParentId(Long parentId) {
		return sysMenuDao.queryListParentId(parentId);
	}

	@Override
	public List<SysMenuEntity> queryNotButtonList() {
		return sysMenuDao.queryNotButtonList();
	}

	@Override
	public List<SysMenuEntity> getUserMenuList(Long userId) {
		//系统管理员，拥有最高权限
		if(userId == Constant.SUPER_ADMIN){
			return getAllMenuList(null,userId);
		}

		//用户菜单列表
		List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
		return getAllMenuList(menuIdList,userId);
	}

	@Override
	public void delete(Long menuId){
		//删除菜单
		sysMenuDao.deleteById(menuId);
		Map param = new HashMap();
		param.put("menu_id", menuId);
		//删除菜单与角色关联
		sysRoleMenuService.deleteByMap(param);
	}

	@Override
	public List<SysMenuEntity> selectList() {
		return sysMenuDao.selectList();
	}

	@Override
	public SysMenuEntity selectById(Long parentId) {

		return sysMenuDao.selectById(parentId);
	}

	@Override
	public void insert(SysMenuEntity menu) {
		sysMenuDao.insert(menu);
	}

	@Override
	public void updateById(SysMenuEntity menu) {
		sysMenuDao.updateById(menu);

	}

	/**
	 * 获取所有菜单列表
	 */
	private List<SysMenuEntity> getAllMenuList(List<Long> menuIdList,Long userId){
		//查询根菜单列表
		List<SysMenuEntity> menuList = queryListParentId(0L, menuIdList);
		//递归获取子菜单
		getMenuTreeList(menuList, menuIdList,userId);

		return menuList;
	}

	/**
	 * 递归
	 */
	private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Long> menuIdList,Long userId){
		List<SysMenuEntity> subMenuList = new ArrayList<SysMenuEntity>();

		for(SysMenuEntity entity : menuList){
			//目录
			if(entity.getType() == Constant.MenuType.CATALOG.getValue()) {
			/*	List<String> permiss = sysUserDao.selectPermissByUserIdAndParentId(userId, entity.getMenuId());
				if (permiss.size() > 0) {
				*//*	for (SysMenuEntity sysMenuEntity : menuList) {
						if (sysMenuEntity.getMenuId() == entity.getMenuId()) {
							//权限
							if (sysMenuEntity.getPermissList() == null) {
								sysMenuEntity.setPermissList(permiss);
							} else {
								sysMenuEntity.getPermissList().addAll(permiss);
							}
						}
					}*//*
					entity.setPermissList(permiss);
				}*/
				entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList, userId));
			}

			subMenuList.add(entity);
		}

		return subMenuList;
	}

	public List<SysMenuEntity> userrolemenu(Long userid){
		return sysMenuDao.userrolemenu(userid);
	}
}
