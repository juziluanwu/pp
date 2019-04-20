package app.pp.enums;

/**
 * Created by lowdad on 18-1-23. error枚举
 */
public enum ErrorEnum {

	SUCCESS(true, 0, "成功"),
	SUCCESSH(true, 1, "成功"),
	ERROR(false, 1, "成功"),
	SUCCESS1(false, 1, "当前订单已被认领，无法再次认领"),
	SUCCESS2(false, 2, "当前订单已确认过资料，无法再次确认资料"),
	SUCCESS3(false, 3, "当前订单已经处于外派中，请完成外派后再进行外派处理"),
	SUCCESS4(false, 3, "当前业务没有配置，请联系管理员"),
	SUCCESS5(false, 3, "当前投保公司没有配置，请联系管理员"),

	USER_ALREADY_EXISTS(false, 2, "用户已存在"), 
	USER_NOT_EXISTS(false, 5, "用户不存在"), 
	SMS_ERROR(false,3, "验证码错误"), 
	PASSWORD_NOT_EXISTS(false, 6, "未设置密码"), 
	NO_APPROVE(false, 12, "未认证"), 
	WRONG_IDCARD(false, 10, "身份证错误"), 
	MISMATCHING(false, 19, "身份信息有误"), 
	ERROR_PARAM(false, -5, "缺少参数")
	;
	private Boolean isSuccess;
	private Integer code;
	private String msg;

	ErrorEnum(Boolean isSuccess, Integer code, String msg) {
		this.isSuccess = isSuccess;
		this.code = code;
		this.msg = msg;

	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
