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
@ApiModel(value="MES日志", description="MES日志记录查")
@TableName("tblerptransactionlog")
public class MesLogEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "模块名")
    private String moduleid;

    @ApiModelProperty(value = "方法名")
    private String functionid;

    @ApiModelProperty(value = "计算机名")
    private String computername;

    @ApiModelProperty(value = "当前用户")
    private String curuserno;

    @ApiModelProperty(value = "记录时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdate;

    @ApiModelProperty(value = "返回结果")
    private String result;

    @ApiModelProperty(value = "关键值")
    private String keyvalue;
}
