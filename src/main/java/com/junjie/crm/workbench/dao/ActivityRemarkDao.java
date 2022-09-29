package com.junjie.crm.workbench.dao;

import com.junjie.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {
    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);

    List<ActivityRemark> getRemarkList(String activityId);

    int remarkDelete(String id);

    int saveRemark(ActivityRemark activityRemark);

    int remarkUpdate(ActivityRemark activityRemark);
}
