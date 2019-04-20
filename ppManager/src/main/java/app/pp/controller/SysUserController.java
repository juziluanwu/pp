package app.pp.controller;


import app.pp.common.AbstractController;
import app.pp.common.Result;
import app.pp.entity.SysUserEntity;
import app.pp.enums.ErrorEnum;
import app.pp.service.SysUserRoleService;
import app.pp.service.SysUserService;
import app.pp.utils.Assert;
import app.pp.utils.GlobleUtils;
import app.pp.utils.ResultUtils;
import app.pp.vo.PasswordForm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月31日 上午10:40:10
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController   extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;

	/**
	 * 所有用户列表
	 */
	@GetMapping("/list/{page}")
	public Result list(@RequestParam(value = "username", required = false) String username, @PathVariable("page") Integer page){
		Map params = new HashMap();
		params.put("username",username);
		PageHelper.startPage(null == page ? 1 : page, GlobleUtils.DEFAULT_PAGE_SIZE);
		List<SysUserEntity> list = sysUserService.selectByMap(params);
		PageInfo<SysUserEntity> pageInfo = new PageInfo(list);
		return ResultUtils.result(ErrorEnum.SUCCESSH,pageInfo);
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@GetMapping("/info")
	public Result info(){
		return ResultUtils.result(ErrorEnum.SUCCESSH,getUser());

	}


	/**
	 * 用户信息
	 */
	@GetMapping("/info/{userId}")
//	@RequiresPermissions("sys:user:info")
	public Result info(@PathVariable("userId") Long userId){
		SysUserEntity user = sysUserService.selectById(userId);

		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		return ResultUtils.result(ErrorEnum.SUCCESSH,user);

	}

	/**
	 * 修改登录用户密码
	 */
	@PostMapping("/password")
	public Result password(@RequestBody PasswordForm form){
		Assert.isBlank(form.getNewPassword(), "新密码不为能空");
		
		//sha256加密
		String password = new Sha256Hash(form.getPassword(), getUser().getSalt()).toHex();
		//sha256加密
		String newPassword = new Sha256Hash(form.getNewPassword(), getUser().getSalt()).toHex();
				
		//更新密码
		boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
		if(!flag){
			return ResultUtils.fail("原密码不正确");
		}else{
            return ResultUtils.result(ErrorEnum.SUCCESSH,"更新密码成功");
        }


	}

	
	/**
	 * 保存用户
	 */
	@PostMapping("/save")
	@RequiresPermissions("sys:user:save")
	public Result save(@RequestBody SysUserEntity sysUserEntity){

		sysUserEntity.setCreateUserId(getUserId());
		return sysUserService.save(sysUserEntity);
	}
	
	/**
	 * 修改用户
	 */
	@PostMapping("/update")
	@RequiresPermissions("sys:user:update")
	public Result update(@RequestBody SysUserEntity user){
		user.setCreateUserId(getUserId());
		return sysUserService.update(user);
	}
	
	/**
	 * 删除用户
	 */
	@PostMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public Result delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return ResultUtils.fail("系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return ResultUtils.fail("当前用户不能删除");
		}

		Integer i = sysUserService.deleteBatch(userIds);

		return ResultUtils.result(ErrorEnum.SUCCESSH,i);
	}
}
