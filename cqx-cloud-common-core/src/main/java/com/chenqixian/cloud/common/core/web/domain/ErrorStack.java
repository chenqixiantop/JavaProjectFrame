package com.chenqixian.cloud.common.core.web.domain;

import com.chenqixian.cloud.common.core.web.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(
        value = "ErrorStack",
        description = "错误堆栈信息"
)
@Data
public class ErrorStack extends BaseVO<ErrorStack> {
    String info;
    String stack;
    public ErrorStack(String info, String stack) {
        this.info = info;
        this.stack = stack;
    }
}
