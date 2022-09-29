package com.junjie.crm.settings.service.impl;

import com.junjie.crm.settings.dao.DicTypeDao;
import com.junjie.crm.settings.dao.DicValueDao;
import com.junjie.crm.settings.domain.DicType;
import com.junjie.crm.settings.domain.DicValue;
import com.junjie.crm.settings.service.DicService;
import com.junjie.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DicServiceImpl implements DicService {
    private DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);

    public Map<String, List<DicValue>> getAll() {

        List<DicType> dtlist = dicTypeDao.getTypeList();
        Map<String ,List<DicValue>> map = new HashMap<String, List<DicValue>>();

        for (DicType dicType: dtlist) {

            String code = dicType.getCode();

            List<DicValue> dvlist = dicValueDao.getListByCode(code);

            map.put(code+"List",dvlist);

        }
        return map;
    }
}

















