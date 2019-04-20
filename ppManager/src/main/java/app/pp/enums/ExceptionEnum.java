package app.pp.enums;

/**
 * Created by lowdad on 18-1-23.
 * 主动抛出异常的枚举
 */
public enum ExceptionEnum {
    SMS_ERROR("短信发送失败，请检查网络",-2,null),
    ERROR_WITHNOTOKEN("缺少验证信息",-3,null),
    ERROR_SIGN("sign不匹配",7,null),
    ERROR_TOKEN("token错误",-4,null),
    NO_DATA("数据不存在",-5,null),
    ERROR_REPEAT("数据重复",8,null),
    ERROR_PARAM("参数错误",9,null),
    DATA_DELED("无效数据",-6,null);

	
    private String errMsg;
    private Integer errCode;
    private Object Data;


    ExceptionEnum(String errMsg, Integer errCode, Object data) {
        this.errMsg = errMsg;
        this.errCode = errCode;
        this.Data = data;
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
