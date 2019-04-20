package app.pp.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@Component
public class HttpResponseFilter implements Filter {
    private final static Logger logger= LoggerFactory.getLogger(HttpResponseFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
/** * 全局拦截器，做请求头跨域验证*/


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response1 = (HttpServletResponse)response;
        response1.setHeader("Access-Control-Allow-Origin", "*");
        response1.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,OPTIONS,DELETE");
        response1.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type,token,sign,userid,timestamp");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
