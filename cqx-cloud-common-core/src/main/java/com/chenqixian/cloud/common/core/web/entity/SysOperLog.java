package com.chenqixian.cloud.common.core.web.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 53486
 */
@Data
public class SysOperLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String operId;
    private String title;
    private String businessType;
    private String[] businessTypes;
    private String method;
    private String requestMethod;
    private String operatorType;
    private String operName;
    private String deptName;
    private String operUrl;
    private String operIp;
    private String operParam;
    private String jsonResult;
    private String status;
    private String errorMsg;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date operTime;
}
