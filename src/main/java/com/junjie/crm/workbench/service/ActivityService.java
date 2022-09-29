package com.junjie.crm.workbench.service;

import com.junjie.crm.vo.PaginationVO;
import com.junjie.crm.workbench.domain.Activity;
import com.junjie.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    boolean save(Activity activity);

    PaginationVO<Activity> pageList(Map<String, Object> map);

    boolean delete(String[] ids);

    Map<String, Object> getUserListAndActivity(String id);

    boolean update(Activity activity);

    Activity detail(String id);

    List<ActivityRemark> getRemarkListByAid(String activityId);

    boolean remarkDelete(String id);

    boolean saveRemark(ActivityRemark activityRemark);

    boolean remarkUpdate(ActivityRemark activityRemark);
}
