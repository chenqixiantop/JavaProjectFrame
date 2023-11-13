package com.chenqixian.cloud.common.core.web.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.chenqixian.cloud.common.core.web.validation.Update;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class BaseEntity<T> extends Model {
    @ApiModelProperty(
            value = "主键",
            example = "0"
    )
    @TableId(
            type = IdType.ASSIGN_UUID
    )
    @JsonSerialize(
            using = ToStringSerializer.class
    )
    @NotNull(
            message = "主键不能为空",
            groups = {Update.class}
    )
    private String id;
    @ApiModelProperty(
            value = "创建人编码",
            example = "0",
            hidden = true,
            accessMode = AccessMode.READ_ONLY,
            readOnly = true
    )
    @TableField(
            value = "create_id",
            fill = FieldFill.INSERT
    )
    @JsonSerialize(
            using = ToStringSerializer.class
    )
    private String createId;
    @ApiModelProperty(
            value = "创建时间",
            hidden = true,
            accessMode = AccessMode.READ_ONLY,
            readOnly = true
    )
    @TableField(
            value = "create_time",
            fill = FieldFill.INSERT
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private LocalDateTime createTime;
    @ApiModelProperty(
            value = "修改人编码",
            example = "0",
            hidden = true,
            accessMode = AccessMode.READ_ONLY,
            readOnly = true
    )
    @TableField(
            value = "update_id",
            fill = FieldFill.UPDATE
    )
    @JsonSerialize(
            using = ToStringSerializer.class
    )
    private String updateId;
    @ApiModelProperty(
            value = "修改时间",
            hidden = true,
            accessMode = AccessMode.READ_ONLY,
            readOnly = true
    )
    @TableField(
            value = "update_time",
            fill = FieldFill.UPDATE
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private LocalDateTime updateTime;
}
