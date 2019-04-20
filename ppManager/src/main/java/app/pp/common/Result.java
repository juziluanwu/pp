package app.pp.common;

/**
 * Created by lowdad on 18-1-23.
 */
public class Result {
	private Boolean success;
    private Integer errCode;
    private String errMsg;
    private Object Data;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "errCode=" + errCode +
                ", errMsg='" + errMsg + '\'' +
                ", Data=" + Data +
                '}';
    }
}
