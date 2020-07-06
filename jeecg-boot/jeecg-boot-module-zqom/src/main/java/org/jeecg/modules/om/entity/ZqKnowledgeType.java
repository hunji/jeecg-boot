package org.jeecg.modules.om.entity;

import java.io.Serializable;
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

/**
 * @Description: 知识库类型
 * @Author: jeecg-boot
 * @Date:   2020-07-05
 * @Version: V1.0
 */
@Data
@TableName("zq_knowledge_type")
@ApiModel(value="zq_knowledge_type对象", description="知识库类型")
public class ZqKnowledgeType implements Serializable {
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
	/**父级节点*/
	@Excel(name = "父级节点", width = 15)
	@ApiModelProperty(value = "父级节点")
	private java.lang.String pid;
	/**是否有子节点*/
	@Excel(name = "是否有子节点", width = 15, dicCode = "yn")
	@Dict(dicCode = "yn")
	@ApiModelProperty(value = "是否有子节点")
	private java.lang.String hasChild;
	/**名称*/
	@Excel(name = "名称", width = 15)
	@ApiModelProperty(value = "名称")
	private java.lang.String name;
	/**图片*/
	@Excel(name = "图片", width = 15)
	@ApiModelProperty(value = "图片")
	private java.lang.String pics;
	/**类型说明*/
	@Excel(name = "类型说明", width = 15)
	@ApiModelProperty(value = "类型说明")
	private java.lang.String content;
	/**排序字段*/
	@Excel(name = "排序字段", width = 15)
	@ApiModelProperty(value = "排序字段")
	private java.lang.Integer ordernum;
}
