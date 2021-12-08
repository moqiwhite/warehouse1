package com.exam.examzuul.filters;

import com.exam.examzuul.config.RedisUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class TokenFilter extends ZuulFilter {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        System.out.println("run.....");
        RequestContext context = RequestContext.getCurrentContext();

        HttpServletRequest request = context.getRequest();

        String type = request.getHeader("type");

        if(null==type){
            //其他
            String token = request.getHeader("token");
            if (token==null){
                context.setSendZuulResponse(false);
                context.setResponseStatusCode(401);
                context.setResponseBody("没有token，不能访问");
                System.out.println("没有token，不能访问");
                return "没有token，不能访问";
            }else{
                boolean b = redisUtils.exist(token);
                if (b){
                    return "pass";
                }else {
                    context.setSendZuulResponse(false);
                    context.setResponseStatusCode(402);
                    context.setResponseBody("token错误或过期，不能访问");
                    System.out.println("token错误或过期，不能访问");
                    return "token错误或过期，不能访问";
                }
            }
        }
        else{
            //登录
            System.out.println("login...");
            return "pass";

        }
    }
}
