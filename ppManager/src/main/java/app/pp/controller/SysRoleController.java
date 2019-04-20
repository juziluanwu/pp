package app.pp.controller;



import app.pp.common.AbstractController;
import app.pp.common.Result;
import app.pp.entity.SysRoleEntity;
import app.pp.enums.ErrorEnum;
import app.pp.service.SysRoleMenuService;
import app.pp.service.SysRoleService;
import app.pp.utils.GlobleUtils;
import app.pp.utils.ResultUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 角色管理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月8日 下午2:18:33
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController  extends AbstractController {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;

	/**
	 * 角色列表
	 */
	@GetMapping("/list/{page}")
	public Result list(@RequestParam(value = "roleName", required = false) String roleName,
					   @RequestParam(value = "type", required = false) Integer type,
					   @PathVariable("page") Integer page){
		Map<String, Object> map = new HashMap<>();
		map.put("roleName",roleName);
		map.put("type",type);
		List<SysRoleEntity>  list = sysRoleService.selectByMap(map);
		PageHelper.startPage(null == page ? 1 : page, GlobleUtils.DEFAULT_PAGE_SIZE);
		PageInfo<SysRoleEntity> pageInfo = new PageInfo(list);
		return ResultUtils.result(ErrorEnum.SUCCESSH,pageInfo);
	}

	/**
	 * 角色列表
	 */
	@GetMapping("/select")
	public Result select(){
		Map<String, Object> map = new HashMap<>();
		List<SysRoleEntity> list = sysRoleService.selectByMap(map);
		return ResultUtils.result(ErrorEnum.SUCCESSH,list);

	}

	/**
	 * 角色信息
	 */
	@GetMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	public Result info(@PathVariable("roleId") Long roleId){
		SysRoleEntity role = sysRoleService.selectById(roleId);
		//查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.queryMenuIdListType2(roleId);
		role.setMenuIdList(menuIdList);
		return ResultUtils.result(ErrorEnum.SUCCESSH,role);

	}

	/**
	 * 保存角色
	 */
	@PostMapping("/save")
	@RequiresPermissions("sys:role:save")
	public Result save(@RequestBody SysRoleEntity role){
		role.setCreateUserId(getUserId());
		role.setCreateTime(new Date());
		sysRoleService.save(role);
		return ResultUtils.result(ErrorEnum.SUCCESSH,"添加成功");
	}

	/**
	 * 修改角色
	 */
	@PostMapping("/update")
	@RequiresPermissions("sys:role:update")
	public Result update(@RequestBody SysRoleEntity role){
		sysRoleService.update(role);
		return ResultUtils.result(ErrorEnum.SUCCESSH,"修改成功");
	}

	/**
	 * 删除角色
	 */
	@PostMapping("/delete")
	@RequiresPermissions("sys:role:delete")
	public Result delete(@RequestBody Long[] roleIds){
		sysRoleService.deleteBatch(roleIds);
		return ResultUtils.result(ErrorEnum.SUCCESSH,"删除成功");
	}
}
