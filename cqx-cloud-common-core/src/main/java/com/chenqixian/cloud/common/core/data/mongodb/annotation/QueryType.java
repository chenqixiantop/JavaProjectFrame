package com.chenqixian.cloud.common.core.data.mongodb.annotation;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author 53486
 */

public enum QueryType {
    EQUALS {
        @Override
        public Criteria buildCriteria(QueryField queryFieldAnnotation, Field field, Object value) {
            if (QueryType.check(queryFieldAnnotation, field, value)) {
                String queryField = QueryType.getQueryFieldName(queryFieldAnnotation, field);
                return Criteria.where(queryField).is(value.toString());
            } else {
                return new Criteria();
            }
        }
    },
    LIKE {
        @Override
        public Criteria buildCriteria(QueryField queryFieldAnnotation, Field field, Object value) {
            if (QueryType.check(queryFieldAnnotation, field, value)) {
                String queryField = QueryType.getQueryFieldName(queryFieldAnnotation, field);
                return Criteria.where(queryField).regex(value.toString());
            } else {
                return new Criteria();
            }
        }
    },
    IN {
        @Override
        public Criteria buildCriteria(QueryField queryFieldAnnotation, Field field, Object value) {
            if (QueryType.check(queryFieldAnnotation, field, value) && value instanceof List) {
                String queryField = QueryType.getQueryFieldName(queryFieldAnnotation, field);
                return Criteria.where(queryField).in((List)value);
            } else {
                return new Criteria();
            }
        }
    };

    private QueryType() {
    }

    private static boolean check(QueryField queryField, Field field, Object value) {
        return queryField != null && field != null && value != null;
    }

    public abstract Criteria buildCriteria(QueryField var1, Field var2, Object var3);

    private static String getQueryFieldName(QueryField queryField, Field field) {
        String queryFieldValue = queryField.attribute();
        if (!StringUtils.hasText(queryFieldValue)) {
            queryFieldValue = field.getName();
        }

        return queryFieldValue;
    }
}
