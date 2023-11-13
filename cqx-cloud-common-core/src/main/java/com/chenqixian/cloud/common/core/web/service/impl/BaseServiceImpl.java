package com.chenqixian.cloud.common.core.web.service.impl;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenqixian.cloud.common.core.web.dao.BaseDao;
import com.chenqixian.cloud.common.core.web.service.BaseService;

public class BaseServiceImpl<M extends BaseDao<T>, T extends Model> extends ServiceImpl<M, T> implements BaseService<T> {
    public BaseServiceImpl() {
    }
}
