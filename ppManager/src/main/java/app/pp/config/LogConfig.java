package app.pp.config;

import app.pp.entity.Log;
import app.pp.entity.SysUserEntity;
import app.pp.mapper.LogMapper;
import com.google.gson.Gson;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@Component
@Aspect
public class LogConfig {
    private static Logger logger = LoggerFactory.getLogger(LogConfig.class);

    @Autowired
    private LogMapper logMapper;


    private String requestPath = null ; // 请求地址
    private String args = null; //方法里的参数
    private Map<?,?> inputParamMap = null ; // 传入参数
/*    private Map<String, Object> outputParamMap = null; // 存放输出结果
    private long startTimeMillis = 0; // 开始时间
    private long endTimeMillis = 0; // 结束时间*/

    @Before("execution(* app.pp.controller.*.*(..))")
    public void before(JoinPoint pjp) throws Throwable {
        /**
         * 1.获取request信息
         * 2.根据request获取session
         * 3.从session中取出登录用户信息
         */
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;
        HttpServletRequest request = sra.getRequest();
        // 获取输入参数
        inputParamMap = request.getParameterMap();
        // 获取请求地址
        requestPath = request.getRequestURI();

        args = Arrays.toString(pjp.getArgs());
        logger.info(args);
        Gson gson = new Gson();
        Log log = new Log();
        log.setContent(requestPath);
        log.setRecord(gson.toJson(inputParamMap));
        log.setAddtime(new Date());
        try {
            SysUserEntity s = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
            log.setAdduser(s.getUsername());
        }catch (Exception e){
            logger.info("登录");
        }
        logMapper.insert(log);
        //pjp.proceed();

        // 执行完方法的返回值：调用proceed()方法，就会触发切入点方法执行
       /* outputParamMap = new HashMap<String, Object>();
        Object result = pjp.proceed();// result的值就是被拦截方法的返回值
        outputParamMap.put("result", result);

        return result;*/
    }
}
