package app.pp.handle;


import app.pp.exceptions.GlobleException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一异常处理类
 * Created by wcy on 2018/1/10.
 */
@ControllerAdvice
public class ExceptionHandle {
    private final static Logger logger= LoggerFactory.getLogger(ExceptionHandle.class);
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Map<String,Object> handle(Exception e){
        Map<String,Object> map=new HashMap<>();
        if(e instanceof GlobleException){
            logger.error("【异常】 {}",((GlobleException) e).getErrMsg());
            GlobleException globleException = (GlobleException) e;
            map.put("errMsg", globleException.getErrMsg());
            map.put("errCode", globleException.getErrCode());
            map.put("Data", globleException.getData());
            map.put("success",false);
            return map;
        }else if(e instanceof UnauthorizedException){

            map.put("errMsg", "用户无权限");
            map.put("success",false);
            return map;
        }else{
            //e.printStackTrace();
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String msg=sw.toString();
            logger.error("【系统异常】 {"+msg+"}");
            map.put("errMsg","系统异常,请联系管理员");
            map.put("success",false);
            /**
             * throw new Exceptions("msg")
             */
            return map;
        }
    }
/*    *//**
     * 处理自定义异常
     *//*
    @ExceptionHandler(GlobleException.class)
    public Result handleRRException(GlobleException e){

        return ResultUtils.fail(e.getErrMsg());
    }*/
}
