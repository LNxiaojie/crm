package com.junjie.crm.web.listener;

import com.junjie.crm.settings.domain.DicValue;
import com.junjie.crm.settings.service.DicService;
import com.junjie.crm.settings.service.impl.DicServiceImpl;
import com.junjie.crm.utils.ServiceFactory;
import com.junjie.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SysInitListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {

        System.out.println("服务器加载数据字典开始");

        ServletContext application = event.getServletContext();

        DicService dicService = (DicService) ServiceFactory.getService(new DicServiceImpl());

        Map<String , List<DicValue>> map = dicService.getAll();

        //得到map集合当中所有的key值，并且存到set集合当中
        Set<String> set = map.keySet();


        for (String key: set) {
            //在此处将set集合当中的key值得到，由key值得到map集合当中所存储的value值
            application.setAttribute(key,map.get(key));
        }

        System.out.println("服务器加载数据字典结束");

    }
}














