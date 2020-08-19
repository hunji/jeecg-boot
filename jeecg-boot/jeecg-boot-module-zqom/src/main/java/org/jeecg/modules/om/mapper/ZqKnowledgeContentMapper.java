package org.jeecg.modules.om.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.om.entity.ZqKnowledgeContent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 知识内容信息
 * @Author: jeecg-boot
 * @Date:   2020-07-09
 * @Version: V1.0
 */
public interface ZqKnowledgeContentMapper extends BaseMapper<ZqKnowledgeContent> {
    Integer submitBatch(@Param("ids") String[] ids, @Param("status")int status);
}
