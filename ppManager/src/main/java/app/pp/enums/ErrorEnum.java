package app.pp.enums;

/**
 * Created by lowdad on 18-1-23. error枚举
 */
public enum ErrorEnum {

	SUCCESS(true, 0, "成功");
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
