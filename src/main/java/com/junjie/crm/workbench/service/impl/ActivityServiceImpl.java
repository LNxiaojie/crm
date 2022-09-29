package com.junjie.crm.workbench.service.impl;

import com.junjie.crm.settings.dao.UserDao;
import com.junjie.crm.settings.domain.User;
import com.junjie.crm.utils.SqlSessionUtil;
import com.junjie.crm.vo.PaginationVO;
import com.junjie.crm.workbench.dao.ActivityDao;
import com.junjie.crm.workbench.dao.ActivityRemarkDao;
import com.junjie.crm.workbench.domain.Activity;
import com.junjie.crm.workbench.domain.ActivityRemark;
import com.junjie.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {

    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    public boolean save(Activity activity) {

        boolean flag = true;

        int count = activityDao.save(activity);

        if (count != 1 ){

            flag = false;

            return false;
        }

        return  true;
    }

    public PaginationVO<Activity> pageList(Map<String, Object> map) {

        int total = activityDao.getTotalByCondition(map);

        List<Activity> dataList = activityDao.getActivityListByCondition(map);


        PaginationVO<Activity> paginationVO = new PaginationVO<Activity>();



        paginationVO.setTotal(total);
        paginationVO.setDataList(dataList);

        return paginationVO;
    }

    public boolean delete(String[] ids) {

        boolean flag = false;
        //查询需要删除的条数
        int count1 = activityRemarkDao.getCountByAids(ids);
        //查询已删除的条数
        int count2 = activityRemarkDao.deleteByAids(ids);

        if (count1 == count2){
            flag = true;
        }

        int count3 = activityDao.delete(ids);

        if (count3 == ids.length){
            flag = true;
        }

        return flag;
    }

    public Map<String, Object> getUserListAndActivity(String id) {

        //取uList
        List<User> userList = userDao.getUserList();
        //取activity
        Activity activity = activityDao.getById(id);
        //将两个元素放入map集合当中
        Map<String ,Object> map = new HashMap<String, Object>();

        map.put("uList",userList);
        map.put("a",activity);

        return map;
    }

    public boolean update(Activity activity) {
        boolean flag = true;

        int count = activityDao.update(activity);

        if (count != 1 ){

            flag = false;

            return false;
        }

        return  true;
    }

    public Activity detail(String id) {

        Activity activity = activityDao.detail(id);
        return activity;
    }

    public List<ActivityRemark> getRemarkListByAid(String activityId) {

        List<ActivityRemark> aList =  activityRemarkDao.getRemarkList(activityId);
        return aList;
    }

    public boolean remarkDelete(String id) {

        boolean flag = true;

        int count = activityRemarkDao.remarkDelete(id);

        if (count != 1){
            flag = false;
        }

        return flag;
    }

    public boolean saveRemark(ActivityRemark activityRemark) {
        boolean flag = true;

        int count = activityRemarkDao.saveRemark(activityRemark);
        if (count != 1){
            flag = false;
        }
        return flag;
    }

    public boolean remarkUpdate(ActivityRemark activityRemark) {
        boolean flag = true ;

        int count = activityRemarkDao.remarkUpdate(activityRemark);

        if(count!=1){
            flag = false;
        }
        return flag;
    }

}
