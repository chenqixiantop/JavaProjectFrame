package com.chenqixian.cloud.examples.util;

import com.chenqixian.cloud.examples.model.Employee;
import com.chenqixian.cloud.examples.model.Product;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 53486
 */
public class GroupByStreamDemo {

    public static void main(String[] args) {
        Employee emp1 = new Employee("湖南", "张三", 20, 90.00d, BigDecimal.valueOf(16000));
        Employee emp2 = new Employee("浙江", "李静", 25, 80.00d, BigDecimal.valueOf(13000));
        Employee emp3 = new Employee("广东", "苏恒", 28, 70.00d, BigDecimal.valueOf(18000));
        Employee emp4 = new Employee("广东", "赵娴", 27, 85.00d, BigDecimal.valueOf(19000));

        List<Employee> employees = Lists.newArrayList(emp1,emp2,emp3,emp4);

        /**
         * 使用java8 stream groupBy 操作 按城市分组List
         */
        Map<String, List<Employee>> map = employees.stream().collect(Collectors.groupingBy(Employee::getCity));

        /**
         * 使用java8 stream groupBy 操作 统计每个分组的count
         */
        Map<String, Long> map1 = employees.stream().collect(Collectors.groupingBy(Employee::getCity, Collectors.counting()));


    }

}
