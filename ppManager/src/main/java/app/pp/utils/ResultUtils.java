package app.pp.utils;


import app.pp.common.Result;
import app.pp.enums.ErrorEnum;


/**
 * Created by lowdad on 18-1-23.
 */
public class ResultUtils {

    public static Result result(ErrorEnum errorEnum, Object object){
        Result result = new Result();
        result.setSuccess(errorEnum.getIsSuccess());
        result.setErrCode(errorEnum.getCode());
        result.setErrMsg(errorEnum.getMsg());
        result.setData(object);
        return result;
    }
    
    public static Result fail(int code,String msg){
    	Result result = new Result();
        result.setSuccess(false);
        result.setErrCode(code);
        result.setErrMsg(msg);
        result.setData(null);
        return result;
    }
    
    public static Result fail(String msg){
    	Result result = new Result();
        result.setSuccess(false);
        result.setErrCode(-1);
        result.setErrMsg(msg);
        result.setData(null);
        return result;
    }
}
