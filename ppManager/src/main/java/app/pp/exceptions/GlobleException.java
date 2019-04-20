package app.pp.exceptions;

import app.pp.enums.ExceptionEnum;

/**
 * Created by lowdad on 18-1-23.
 * 主动抛出异常
 */
public class GlobleException extends RuntimeException{
    private String errMsg;
    private Integer errCode;
    private Object Data;
    
    public GlobleException(String msg){
    	this.errMsg = msg;
        this.errCode = 400;
        this.Data = null;
    }

    public GlobleException(ExceptionEnum exceptionEnum) {
        this.errMsg = exceptionEnum.getErrMsg();
        this.errCode = exceptionEnum.getErrCode();
        this.Data = exceptionEnum.getData();
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }
}
