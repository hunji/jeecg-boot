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
 * @Description: 知识内容信息
 * @Author: hunji
 * @Date:   2020-07-09
 * @Version: V1.0
 */
@Data
@TableName("zq_knowledge_content")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="zq_knowledge_content对象", description="知识内容信息")
public class ZqKnowledgeContent implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_UUID)
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
	/**标题*/
	@Excel(name = "标题", width = 15)
    @ApiModelProperty(value = "标题")
    private java.lang.String title;
	/**简要描述*/
	@Excel(name = "简要描述", width = 15)
    @ApiModelProperty(value = "简要描述")
    private java.lang.String brief;
	/**内容*/
	@Excel(name = "内容", width = 15)
    @ApiModelProperty(value = "内容")
    private java.lang.String content;
	/**0.未审核 1.已审核*/
    @Dict(dicCode = "approval_status")
	@Excel(name = "0.未审核 1.已审核", width = 15)
    @ApiModelProperty(value = "0.未审核 1.已审核")
    private java.lang.Integer reviewState;
	/**审核时间*/
	@Excel(name = "审核时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "审核时间")
    private java.util.Date reviewDate;
	/**重要程度--评级时候判断*/
	@Excel(name = "重要程度--评级时候判断", width = 15)
    @ApiModelProperty(value = "重要程度--评级时候判断")
    private java.lang.Integer rank;
	/**点赞数目--在录入的时候不需要录入，当知识返回后可以保存之前的数据*/
	@Excel(name = "点赞数目", width = 15)
    @ApiModelProperty(value = "点赞数目")
    private java.lang.Integer likeNum;
	/**浏览数目*/
	@Excel(name = "浏览数目", width = 15)
    @ApiModelProperty(value = "浏览数目")
    private java.lang.Integer viewNum;
	/**类型 编号*/
	@Excel(name = "类型 编号", width = 15, dictTable = "zq_knowledge_type", dicText = "name", dicCode = "id")
	@Dict(dictTable = "zq_knowledge_type", dicText = "type_name", dicCode = "id")
    @ApiModelProperty(value = "类型 编号")
    private java.lang.String typeId;
}
