package org.jeecg.modules.om.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 日常问题
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
@Data
@TableName("zq_question_content")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="zq_question_content对象", description="日常问题")
public class ZqQuestionContent implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**提出人*/
	@Excel(name = "提出人", width = 15)
    @ApiModelProperty(value = "提出人")
    private java.lang.String proposer;
	/**简要描述*/
	@Excel(name = "简要描述", width = 15)
    @ApiModelProperty(value = "简要描述")
    private java.lang.String brief;
	/**详情*/
	@Excel(name = "详情", width = 15)
    @ApiModelProperty(value = "详情")
    private java.lang.String content;
	/**解决方案*/
	@Excel(name = "解决方案", width = 15)
    @ApiModelProperty(value = "解决方案")
    private java.lang.String solution;
	/**是否解决*/
    @Dict(dicCode = "yn")
	@Excel(name = "是否解决", width = 15)
    @ApiModelProperty(value = "是否解决")
    private java.lang.Integer solutionState;
	/**问题类型*/
	@Excel(name = "问题类型", width = 15, dictTable = "zq_question_type", dicText = "name", dicCode = "id")
	@Dict(dictTable = "zq_question_type", dicText = "name", dicCode = "id")
    @ApiModelProperty(value = "问题类型")
    private java.lang.Double typeId;
}
