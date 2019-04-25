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

package app.pp.controller;


import app.pp.common.AbstractController;
import app.pp.common.Result;
import app.pp.entity.SysMenuEntity;
import app.pp.enums.ErrorEnum;
import app.pp.exceptions.GlobleException;
import app.pp.service.ShiroService;
import app.pp.service.SysMenuService;
import app.pp.utils.Constant;
import app.pp.utils.ResultUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统菜单
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午9:58:15
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {
	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private ShiroService shiroService;

	/**
	 * 导航菜单
	 */
	@GetMapping("/nav")
	public Result nav(){
		List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(getUserId());
		Map result = new HashMap();
		result.put("menuList", menuList);
		return ResultUtils.result(ErrorEnum.SUCCESS,result);
	}

	/**
	 *  查询菜单对应的权限
	 */

	@GetMapping("/navPermission")
	public Result navPermission(){

		List<String> permissions = shiroService.getPermissions(getUserId());
		return ResultUtils.result(ErrorEnum.SUCCESS,permissions);
	}

	/*	*//**
	 *  检验是否有按钮使用权限
	 *//*
	@GetMapping("/navPermission/{menuId}/{permission}")
	public Result navPermission(@PathVariable("menuId") Long menuId,@PathVariable("permission") String permission){
		List<String> permissions = shiroService.getPermissions(getUserId(),menuId);
		if (permissions.contains(permission)){
			return ResultUtils.result(ErrorEnum.SUCCESS,null);
		}else{
			return ResultUtils.fail("没有访问权限");
		}
	}*/
	/**
	 * 所有菜单列表
	 */
	@GetMapping("/list")
	public Result  list(){
		List<SysMenuEntity> menuList = sysMenuService.selectList();
		for(SysMenuEntity sysMenuEntity : menuList){
			SysMenuEntity parentMenuEntity = sysMenuService.selectById(sysMenuEntity.getParentId());
			if(parentMenuEntity != null){
				sysMenuEntity.setParentName(parentMenuEntity.getName());
			}
		}
		return ResultUtils.result(ErrorEnum.SUCCESS,menuList);
	}
	/**
	 * 用户菜单列表
	 */
	@GetMapping("/userrolemenu")
	public Result  userrolemenu(){
		List<SysMenuEntity> menuList = null;
		//int type = getUser().getType();
		if(getUserId() == Constant.SUPER_ADMIN  ){
			menuList = sysMenuService.selectList();
		}else{
			menuList = sysMenuService.userrolemenu(getUserId());
		}

		for(SysMenuEntity sysMenuEntity : menuList){
			SysMenuEntity parentMenuEntity = sysMenuService.selectById(sysMenuEntity.getParentId());
			if(parentMenuEntity != null){
				sysMenuEntity.setParentName(parentMenuEntity.getName());
			}
		}
		return ResultUtils.result(ErrorEnum.SUCCESS,menuList);
	}
	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@GetMapping("/select")
	public Result select(){
		//查询列表数据
		List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList();

		//添加顶级菜单
		SysMenuEntity root = new SysMenuEntity();
		root.setMenuId(0L);
		root.setName("一级菜单");
		root.setParentId(-1L);
		root.setOpen(true);
		menuList.add(root);
		return ResultUtils.result(ErrorEnum.SUCCESS,menuList);
	}

	/**
	 * 菜单信息
	 */

	@GetMapping("/info/{menuId}")
	//@RequiresPermissions("sys:menu:info")
	public Result info(@PathVariable("menuId") Long menuId){
		SysMenuEntity menu = sysMenuService.selectById(menuId);
		return ResultUtils.result(ErrorEnum.SUCCESS,menu);
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	//@RequiresPermissions("sys:menu:save")
	public Result save(@RequestBody SysMenuEntity menu){
		//数据校验
		verifyForm(menu);

		sysMenuService.insert(menu);

		return ResultUtils.result(ErrorEnum.SUCCESS,"保存成功");
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	//@RequiresPermissions("sys:menu:update")
	public Result update(@RequestBody SysMenuEntity menu){
		//数据校验
		verifyForm(menu);

		sysMenuService.updateById(menu);

		return ResultUtils.result(ErrorEnum.SUCCESS,"修改成功");
	}

	/**
	 * 删除
	 */
	@GetMapping("/delete/{menuId}")
	//@RequiresPermissions("sys:menu:delete")
	public Result delete(@PathVariable("menuId") long menuId){
		if(menuId <= 31){
			return ResultUtils.fail("系统菜单，不能删除");
		}
		//判断是否有子菜单或按钮
		List<SysMenuEntity> menuList = sysMenuService.queryListParentId(menuId);
		if(menuList.size() > 0){
			return ResultUtils.fail("请先删除子菜单或按钮");
		}
		sysMenuService.delete(menuId);

		return ResultUtils.result(ErrorEnum.SUCCESS,"删除成功");
	}

	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(SysMenuEntity menu){
		if(StringUtils.isBlank(menu.getName())){
			throw new GlobleException("菜单名称不能为空");
		}

		if(menu.getParentId() == null){
			throw new GlobleException("上级菜单不能为空");
		}

		//菜单
		if(menu.getType() == Constant.MenuType.MENU.getValue()){
			if(StringUtils.isBlank(menu.getUrl())){
				throw new GlobleException("菜单URL不能为空");
			}
		}


		//上级菜单类型
		int parentType = Constant.MenuType.CATALOG.getValue();
		if(menu.getParentId() != 0){
			SysMenuEntity parentMenu = sysMenuService.selectById(menu.getParentId());
			parentType = parentMenu.getType();
		}

		//目录、菜单
		if(menu.getType() == Constant.MenuType.CATALOG.getValue() ||
				menu.getType() == Constant.MenuType.MENU.getValue()){
			if(parentType != Constant.MenuType.CATALOG.getValue()){
				throw new GlobleException("上级菜单只能为目录类型");
			}
			return ;
		}

		//按钮
		if(menu.getType() == Constant.MenuType.BUTTON.getValue()){
			if(parentType != Constant.MenuType.MENU.getValue()){
				throw new GlobleException("上级菜单只能为菜单类型");
			}
			return ;
		}
	}
}
