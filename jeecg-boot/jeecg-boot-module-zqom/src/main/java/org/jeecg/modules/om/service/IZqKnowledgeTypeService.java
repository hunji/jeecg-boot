package org.jeecg.modules.om.service;

import org.jeecg.modules.om.entity.ZqKnowledgeType;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.exception.JeecgBootException;

/**
 * @Description: 知识库类型
 * @Author: jeecg-boot
 * @Date:   2020-07-05
 * @Version: V1.0
 */
public interface IZqKnowledgeTypeService extends IService<ZqKnowledgeType> {

	/**根节点父ID的值*/
	public static final String ROOT_PID_VALUE = "0";
	
	/**树节点有子节点状态值*/
	public static final String HASCHILD = "1";
	
	/**树节点无子节点状态值*/
	public static final String NOCHILD = "0";

	/**新增节点*/
	void addZqKnowledgeType(ZqKnowledgeType zqKnowledgeType);
	
	/**修改节点*/
	void updateZqKnowledgeType(ZqKnowledgeType zqKnowledgeType) throws JeecgBootException;
	
	/**删除节点*/
	void deleteZqKnowledgeType(String id) throws JeecgBootException;

}
