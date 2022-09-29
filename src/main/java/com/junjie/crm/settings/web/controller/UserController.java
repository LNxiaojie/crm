package com.junjie.crm.settings.web.controller;

import com.junjie.crm.settings.domain.User;
import com.junjie.crm.settings.service.UserService;
import com.junjie.crm.settings.service.impl.UserServiceImpl;
import com.junjie.crm.utils.MD5Util;
import com.junjie.crm.utils.PrintJson;
import com.junjie.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet{

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入用户控制器");
        String path = request.getServletPath();
        if("/settings/user/login.do".equals(path)){
            login(request,response);
        }else{
            //xxx(requset,response);
        }

    }

    private void login(HttpServletRequest request, HttpServletResponse response) {

        String loginAct =  request.getParameter("loginAct");
        String loginPwd =  request.getParameter("loginPwd");

        loginPwd = MD5Util.getMD5(loginPwd);

        String ip= request.getRemoteAddr();

        System.out.println("ip============="+ip);

        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());

        try{

            System.out.println("进入try catch语句块");

            User user = userService.login(loginAct,loginPwd,ip);
            request.getSession().setAttribute("user",user);
            PrintJson.printJsonFlag(response,true);
        }catch (Exception e){
            e.printStackTrace();
            String msg = e.getMessage();
            System.out.println(msg);
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map);
        }
    }
}
