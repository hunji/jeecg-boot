package org.jeecg.modules.om.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.om.entity.ZqQuestionType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 问题类型
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
public interface ZqQuestionTypeMapper extends BaseMapper<ZqQuestionType> {

	/**
	 * 编辑节点状态
	 * @param id
	 * @param status
	 */
	void updateTreeNodeStatus(@Param("id") String id,@Param("status") String status);

}
