package com.chenqixian.cloud.examples.util;

import com.chenqixian.cloud.examples.model.Employee;
import com.chenqixian.cloud.examples.model.Product;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.IntSummaryStatistics;
import java.util.LinkedHashMap;
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
         * 使用java8 stream groupBy 操作::分组 按城市分组List
         */
        Map<String, List<Employee>> map = employees.stream().collect(Collectors.groupingBy(Employee::getCity));

        /**
         * 使用java8 stream groupBy 操作::求总数 统计每个分组的count
         */
        Map<String, Long> map1 = employees.stream()
                .collect(Collectors.groupingBy(Employee::getCity, Collectors.counting()));

        /**
         * 使用java8 stream groupBy 操作::求平均值 按城市分组list 计算分组里面的平均年龄
         */
        Map<String, Double> map2 = employees.stream()
                .collect(Collectors.groupingBy(Employee::getCity, Collectors.averagingInt(Employee::getAge)));

        Map<String, Double> map3 = employees.stream()
                .collect(Collectors.groupingBy(Employee::getCity, Collectors.averagingDouble(Employee::getDoubleSum)));

        /**
         * 使用java8 stream groupBy 操作::求和 按城市分组list 计算分组里面的年龄总和
         */
        Map<String, Integer> map4 = employees.stream()
                .collect(Collectors.groupingBy(Employee::getCity, Collectors.summingInt(Employee::getAge)));

        Map<String, Double> map5 = employees.stream()
                .collect(Collectors.groupingBy(Employee::getCity, Collectors.summingDouble(Employee::getDoubleSum)));

        // 对Map按照分组年龄总值逆序排序
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        map4.entrySet().stream().sorted(Map.Entry.<String, Integer> comparingByValue().reversed())
                .forEachOrdered(e -> sortedMap.put(e.getKey(), e.getValue()));

        // 对Map按照分组销售总值正序排序
        Map<String, Double> sortedMap2 = new LinkedHashMap<>();
        map5.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .forEachOrdered(e -> sortedMap2.put(e.getKey(), e.getValue()));


    }

}
