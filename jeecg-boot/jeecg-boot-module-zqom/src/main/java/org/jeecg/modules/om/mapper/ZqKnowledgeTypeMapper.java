package org.jeecg.modules.om.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.om.entity.ZqKnowledgeType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 知识库类型
 * @Author: jeecg-boot
 * @Date:   2020-07-05
 * @Version: V1.0
 */
public interface ZqKnowledgeTypeMapper extends BaseMapper<ZqKnowledgeType> {

	/**
	 * 编辑节点状态
	 * @param id
	 * @param status
	 */
	void updateTreeNodeStatus(@Param("id") String id,@Param("status") String status);

}
