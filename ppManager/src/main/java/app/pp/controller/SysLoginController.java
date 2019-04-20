package app.pp.controller;


import app.pp.common.AbstractController;
import app.pp.common.Result;
import app.pp.entity.SysUserEntity;
import app.pp.enums.ErrorEnum;
import app.pp.service.SysUserService;
import app.pp.service.SysUserTokenService;
import app.pp.utils.ResultUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录相关
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月10日 下午1:15:31
 */
@RestController
public class SysLoginController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserTokenService sysUserTokenService;

	/**
	 * 登录
	 */
	@ApiOperation(value = "后台管理系统登录接口", httpMethod = "POST", response = Result.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username", value = "账号", dataTypeClass = String.class, required = true),
			@ApiImplicitParam(name = "password", value = "密码", dataTypeClass = String.class, required = true)})
	@PostMapping("/sys/login")
	public Result login(@RequestParam(value = "username" ,required = true) String username,
						 @RequestParam(value = "password" ,required = true) String password){
		// 用户信息
		SysUserEntity user = sysUserService.queryByUserName(username);

		//账号不存在、密码错误
		if(user == null || !user.getPassword().equals(password)) {
			return ResultUtils.fail("账号或密码不正确");
		}

		//账号锁定
		if(user.getStatus() == 0){
			return ResultUtils.fail("账号已被锁定,请联系管理员");
		}
		//生成token，并保存到数据库
		Map result = sysUserTokenService.createToken(user.getUserId());
		Map results = new HashMap();
		results.put("user",user);
		results.putAll(result);
		return ResultUtils.result(ErrorEnum.SUCCESSH,results);
	}

	/**
	 * 退出
	 */
	@ApiOperation(value = "退出接口" ,httpMethod = "POST" ,response = Result.class)
	@PostMapping("/sys/logout")
	public Result logout() {
		sysUserTokenService.logout(getUserId());
		return ResultUtils.result(ErrorEnum.SUCCESSH,null);
	}
	
}
