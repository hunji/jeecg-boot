package org.jeecg.modules.om.service;

import org.jeecg.modules.om.entity.ZqQuestionType;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.exception.JeecgBootException;

/**
 * @Description: 问题类型
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
public interface IZqQuestionTypeService extends IService<ZqQuestionType> {

	/**根节点父ID的值*/
	public static final String ROOT_PID_VALUE = "0";
	
	/**树节点有子节点状态值*/
	public static final String HASCHILD = "1";
	
	/**树节点无子节点状态值*/
	public static final String NOCHILD = "0";

	/**新增节点*/
	void addZqQuestionType(ZqQuestionType zqQuestionType);
	
	/**修改节点*/
	void updateZqQuestionType(ZqQuestionType zqQuestionType) throws JeecgBootException;
	
	/**删除节点*/
	void deleteZqQuestionType(String id) throws JeecgBootException;

}
