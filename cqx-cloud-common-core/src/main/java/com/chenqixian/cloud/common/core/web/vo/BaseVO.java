package com.chenqixian.cloud.common.core.web.vo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;

import java.io.Serializable;

public class BaseVO<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public BaseVO() {
    }

    public T copyFrom(Object from) {
        BeanUtil.copyProperties(from, this, new String[0]);
        return (T) this;
    }

    public String toJson() {
        return JSONUtil.toJsonStr(this);
    }
}
