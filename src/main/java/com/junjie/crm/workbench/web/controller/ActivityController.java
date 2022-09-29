package com.junjie.crm.workbench.web.controller;

import com.junjie.crm.settings.domain.User;
import com.junjie.crm.settings.service.UserService;
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

public class ActivityController extends HttpServlet{

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入市场活动用户控制器");
        String path = request.getServletPath();
        if("/workbench/activity/getUserList.do".equals(path)){
           getUserList(request,response);
        }else if ("/workbench/activity/save.do".equals(path)){
           save(request,response);
        }else if ("/workbench/activity/pageList.do".equals(path)){
            pageList(request,response);
        }else if ("/workbench/activity/delete.do".equals(path)){
            delete(request,response);
        }else if ("/workbench/activity/getUserListAndActivity.do".equals(path)){
            getUserListAndActivity(request,response);
        }else if ("/workbench/activity/update.do".equals(path)){
            update(request,response);
        }else if ("/workbench/activity/detail.do".equals(path)){
            detail(request,response);
        }else if ("/workbench/activity/getRemarkListByAid.do".equals(path)){
            getRemarkListByAid(request,response);
        }else if ("/workbench/activity/remarkDelete.do".equals(path)){
            remarkDelete(request,response);
        }else if ("/workbench/activity/saveRemark.do".equals(path)){
            saveRemark(request,response);
        }else if ("/workbench/activity/remarkUpdate.do".equals(path)){
            remarkUpdate(request,response);
        }

    }

    private void remarkUpdate(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入市场活动备注信息修改操作");

        String id = request.getParameter("id");
        String noteContent = request.getParameter("noteContent");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User)request.getSession().getAttribute("user")).getName();
        String editFlag = "1";

        ActivityRemark activityRemark = new ActivityRemark();

        activityRemark.setId(id);
        activityRemark.setNoteContent(noteContent);
        activityRemark.setEditFlag(editFlag);
        activityRemark.setEditTime(editTime);
        activityRemark.setEditBy(editBy);

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = activityService.remarkUpdate(activityRemark);
        Map<String ,Object> map = new HashMap<String, Object>();
        map.put("success",flag);
        map.put("ar",activityRemark);

        PrintJson.printJsonObj(response,map);
    }

    private void saveRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入备注添加操作");
        String activityId = request.getParameter("activityId");
        String noteContent = request.getParameter("noteContent");
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String editFlag = "0";

        ActivityRemark activityRemark = new ActivityRemark();
        activityRemark.setActivityId(activityId);
        activityRemark.setCreateBy(createBy);
        activityRemark.setNoteContent(noteContent);
        activityRemark.setId(id);
        activityRemark.setCreateTime(createTime);
        activityRemark.setEditFlag(editFlag);

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = activityService.saveRemark(activityRemark);

        Map<String ,Object> map = new HashMap<String, Object>();
        map.put("success",flag);
        map.put("ar",activityRemark);

        PrintJson.printJsonObj(response,map);

    }

    private void remarkDelete(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进入到删除备注的操作");

        String id = request.getParameter("id");

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = activityService.remarkDelete(id);

        PrintJson.printJsonFlag(response,flag);
    }

    private void getRemarkListByAid(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入市场活动详细信息页的备注列表");

        String activityId = request.getParameter("activityId");

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        List<ActivityRemark> aList = activityService.getRemarkListByAid(activityId);

        PrintJson.printJsonObj(response,aList);

    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入详细信息处理操作");

        String id = request.getParameter("id");

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Activity activity = activityService.detail(id);

        System.out.println(activity);

        request.setAttribute("activity",activity);

        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request,response);

    }

    private void update(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进入市场活动修改操作");

        String id = request.getParameter("id");
        String owner = request.getParameter("owner");
        String name  = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User)request.getSession().getAttribute("user")).getName();


        Activity activity = new Activity();

        activity.setId(id);
        activity.setCost(cost);
        activity.setEditBy(editBy);
        activity.setEditTime(editTime);
        activity.setDescription(description);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = activityService.update(activity);

        PrintJson.printJsonFlag(response,flag);
    }

    private void getUserListAndActivity(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入用户的修改页面");

        String id = request.getParameter("id");

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Map<String ,Object> map = activityService.getUserListAndActivity(id);

        PrintJson.printJsonObj(response,map);


    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进入用户删除模块");

        String ids[] = request.getParameterValues("id");

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = activityService.delete(ids);

        PrintJson.printJsonFlag(response,flag);
        

    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进入市场活动信息查询列表（条件查询+分页查询）");

        String pageNoStr = request.getParameter("pageNo");
        String pageSizeStr = request.getParameter("pageSize");
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startData = request.getParameter("startData");
        String endData = request.getParameter("endData");
        int pageNo = Integer.valueOf(pageNoStr);
        int pageSize = Integer.valueOf(pageSizeStr);

        int skipCount = (pageNo-1)*pageSize;

        Map<String,Object> map = new HashMap<String, Object>();

        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        map.put("owner",owner);
        map.put("name",name);
        map.put("startData",startData);
        map.put("endData",endData);
        map.put("skipCount",skipCount);

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        PaginationVO<Activity> paginationVO = activityService.pageList(map);

        System.out.println(paginationVO);

        PrintJson.printJsonObj(response,paginationVO);

    }

    private void save(HttpServletRequest request, HttpServletResponse response) {

        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String name  = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();

        
        Activity activity = new Activity();

        activity.setId(id);
        activity.setCost(cost);
        activity.setCreateBy(createBy);
        activity.setCreateTime(createTime);
        activity.setDescription(description);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = activityService.save(activity);

        PrintJson.printJsonFlag(response,flag);
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("取得用户的信息");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> userList = us.getUserList();

        PrintJson.printJsonObj(response,userList);

    }

}
