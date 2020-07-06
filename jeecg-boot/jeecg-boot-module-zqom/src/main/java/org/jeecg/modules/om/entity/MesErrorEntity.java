package org.jeecg.modules.om.entity;

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
 * @date 2020-7-6 16:38
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MES错误记录", description="MES错误记录")
@TableName("tblsyserrorlogNew")
public class MesErrorEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户")
    private String userid;

    @ApiModelProperty(value = "记录时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recorddate;

    @ApiModelProperty(value = "服务名称")
    private String servicename;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "字段")
    private String keyfield;

    @ApiModelProperty(value = "值")
    private String keyvalue;

    @ApiModelProperty(value = "计算机名")
    private String computername;
}
