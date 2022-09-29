package com.junjie.crm.settings.dao;

import com.junjie.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueDao {
    List<DicValue> getListByCode(String code);
}
