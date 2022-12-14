package com.junjie.crm.settings.service.impl;

import com.junjie.crm.exception.LoginException;
import com.junjie.crm.settings.dao.UserDao;
import com.junjie.crm.settings.domain.User;
import com.junjie.crm.settings.service.UserService;
import com.junjie.crm.utils.DateTimeUtil;
import com.junjie.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    {
        userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
    }

    public User login(String loginAct, String loginPwd, String ip) throws LoginException {

        Map<String,String> map = new HashMap<String, String>();

        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);

        User user = userDao.login(map);

        if(user == null){
            throw new LoginException("账号密码错误");
        }

        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();

        if(currentTime.compareTo(expireTime)>0){

            throw new LoginException("账号已失效");
        }

        String lockState = user.getLockState();
        if ("0".equals(lockState)){
            throw new LoginException("账号已锁定");
        }

        String allowIps = user.getAllowIps();

        if (!allowIps.contains(ip)){
            throw new LoginException("ip地址受到限制");
        }

        return user;
    }

    public List<User> getUserList() {

        List<User> userList = userDao.getUserList();

        return userList;

    }
}
