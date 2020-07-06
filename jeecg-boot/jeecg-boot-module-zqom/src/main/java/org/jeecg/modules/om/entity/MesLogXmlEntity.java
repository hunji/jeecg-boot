package org.jeecg.modules.om.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author hunji
 * @version 1.0
 * @date 2020-7-6 16:39
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MES异常日志", description="MES异常日志")
@TableName("tblerptransactionxmllog")
public class MesLogXmlEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "message信息")
    private String messagexml;

    @ApiModelProperty(value = "异常栈")
    private String exceptionxml;

    @ApiModelProperty(value = "传入数据结构")
    private String inxml;

    @ApiModelProperty(value = "传出数据结构")
    private String outxml;

    @ApiModelProperty(value = "异常方法")
    private String exceptionfun;

    @ApiModelProperty(value = "记录时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdate;
}
