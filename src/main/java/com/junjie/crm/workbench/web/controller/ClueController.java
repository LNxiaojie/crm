package com.junjie.crm.workbench.web.controller;

import com.junjie.crm.settings.domain.User;
import com.junjie.crm.settings.service.UserService;
import com.junjie.crm.settings.service.impl.DicServiceImpl;
import com.junjie.crm.settings.service.impl.UserServiceImpl;
import com.junjie.crm.utils.DateTimeUtil;
import com.junjie.crm.utils.PrintJson;
import com.junjie.crm.utils.ServiceFactory;
import com.junjie.crm.utils.UUIDUtil;
import com.junjie.crm.vo.PaginationVO;
import com.junjie.crm.workbench.domain.Activity;
import com.junjie.crm.workbench.domain.ActivityRemark;
import com.junjie.crm.workbench.service.ActivityService;
import com.junjie.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClueController extends HttpServlet{

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入线索控制器");
        String path = request.getServletPath();
        if("/workbench/clue/getUserList.do".equals(path)){
           getUserList(request,response);
        }else if ("/workbench/clue/xxx2.do".equals(path)){
           //(request,response);
        }
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进入线索模块取得用户信息操作");

        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> userList = userService.getUserList();

        PrintJson.printJsonObj(response,userList);

    }
}
