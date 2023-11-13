package com.chenqixian.cloud.common.core.data.mongodb.service;

import com.chenqixian.cloud.common.core.data.mongodb.vo.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * @author 53486
 */
public interface BaseMongoDAO<T> {
    T save(T var1);

    void deleteById(T var1);

    void delete(Query var1);

    void deleteByCondition(T var1);

    void updateById(String var1, T var2);

    List<T> findByCondition(T var1);

    List<T> find(Query var1);

    T findOne(Query var1);

    void update(Query var1, Update var2);

    T findById(String var1);

    T findById(String var1, String var2);

    Page<T> findPage(Page<T> var1, Query var2);

    long count(Query var1);

    MongoTemplate getMongoTemplate();
}
