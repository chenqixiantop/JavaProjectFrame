package com.chenqixian.cloud.common.core.data.mongodb.service;

import com.chenqixian.cloud.common.core.data.mongodb.annotation.QueryField;
import com.chenqixian.cloud.common.core.data.mongodb.util.ReflectionUtil;
import com.chenqixian.cloud.common.core.data.mongodb.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author 53486
 */
public class MongoDaoSupport<T> implements BaseMongoDAO<T> {
    @Autowired
    @Qualifier("mongoTemplate")
    protected MongoTemplate mongoTemplate;

    public MongoDaoSupport() {
    }

    @Override
    public T save(T bean) {
        this.mongoTemplate.save(bean);
        return bean;
    }

    @Override
    public void deleteById(T t) {
        this.mongoTemplate.remove(t);
    }

    @Override
    public void delete(Query query) {
        this.mongoTemplate.remove(query);
    }

    @Override
    public void deleteByCondition(T t) {
        Query query = this.buildBaseQuery(t);
        this.mongoTemplate.remove(query, this.getEntityClass());
    }

    @Override
    public void updateById(String id, T t) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Update update = this.buildBaseUpdate(t);
        this.update(query, update);
    }

    @Override
    public List<T> findByCondition(T t) {
        Query query = this.buildBaseQuery(t);
        return this.mongoTemplate.find(query, this.getEntityClass());
    }

    @Override
    public List<T> find(Query query) {
        return this.mongoTemplate.find(query, this.getEntityClass());
    }

    @Override
    public T findOne(Query query) {
        return this.mongoTemplate.findOne(query, this.getEntityClass());
    }

    @Override
    public void update(Query query, Update update) {
        this.mongoTemplate.updateMulti(query, update, this.getEntityClass());
    }

    @Override
    public T findById(String id) {
        return this.mongoTemplate.findById(id, this.getEntityClass());
    }

    @Override
    public T findById(String id, String collectionName) {
        return this.mongoTemplate.findById(id, this.getEntityClass(), collectionName);
    }

    @Override
    public Page<T> findPage(Page<T> page, Query query) {
        query = query == null ? new Query(Criteria.where("_id").exists(true)) : query;
        long count = this.count(query);
        page.setTotal((int)count);
        int currentPage = page.getCurrent();
        int pageSize = page.getSize();
        query.skip((long)((currentPage - 1) * pageSize)).limit(pageSize);
        List<T> rows = this.find(query);
        page.build(rows);
        return page;
    }

    @Override
    public long count(Query query) {
        return this.mongoTemplate.count(query, this.getEntityClass());
    }

    public Query buildBaseQuery(T t) {
        Query query = new Query();
        Field[] fields = t.getClass().getDeclaredFields();
        Field[] var4 = fields;
        int var5 = fields.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Field field = var4[var6];
            field.setAccessible(true);

            try {
                Object value = field.get(t);
                if (value != null) {
                    QueryField queryField = (QueryField)field.getAnnotation(QueryField.class);
                    if (queryField != null) {
                        query.addCriteria(queryField.type().buildCriteria(queryField, field, value));
                    }
                }
            } catch (IllegalArgumentException var10) {
                var10.printStackTrace();
            } catch (IllegalAccessException var11) {
                var11.printStackTrace();
            }
        }

        return query;
    }

    public Update buildBaseUpdate(T t) {
        Update update = new Update();
        Field[] fields = t.getClass().getDeclaredFields();
        Field[] var4 = fields;
        int var5 = fields.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Field field = var4[var6];
            field.setAccessible(true);

            try {
                Object value = field.get(t);
                if (value != null) {
                    update.set(field.getName(), value);
                }
            } catch (Exception var9) {
                var9.printStackTrace();
            }
        }

        return update;
    }

    protected Class<T> getEntityClass() {
        return ReflectionUtil.getSuperClassGenricType(this.getClass());
    }

    @Override
    public MongoTemplate getMongoTemplate() {
        return this.mongoTemplate;
    }
}
