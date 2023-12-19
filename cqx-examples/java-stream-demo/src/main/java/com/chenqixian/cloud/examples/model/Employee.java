package com.chenqixian.cloud.examples.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 53486
 */
@Data
@AllArgsConstructor
public class Employee {
    private String city;

    private String name;

    private Integer age;

    private Double doubleSum;

    private BigDecimal money;
}
