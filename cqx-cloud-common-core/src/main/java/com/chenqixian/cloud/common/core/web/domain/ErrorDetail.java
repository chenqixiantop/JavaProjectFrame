package com.chenqixian.cloud.common.core.web.domain;

import com.chenqixian.cloud.common.core.web.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(
        value = "ErrorDetail",
        description = "错误详情"
)
@Data
public class ErrorDetail extends BaseVO<ErrorDetail> {
    String filed;
    Object value;
    String message;

    public ErrorDetail(String filed, Object value, String message) {
        this.filed = filed;
        this.value = value;
        this.message = message;
    }
}
