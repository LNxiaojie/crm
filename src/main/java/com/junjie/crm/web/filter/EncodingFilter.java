package com.junjie.crm.web.filter;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("进入过滤器，设置字符集编码问题");

        req.setCharacterEncoding("UTF-8");

        resp.setContentType("text/html;charset=utf-8");
        filterChain.doFilter(req,resp);
    }
}
