package org.jeecg.modules.om.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.om.entity.ZqQuestionContent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 日常问题
 * @Author: jeecg-boot
 * @Date:   2020-07-30
 * @Version: V1.0
 */
public interface ZqQuestionContentMapper extends BaseMapper<ZqQuestionContent> {
    /**
     * 批量提交
     * @param ids
     * @return
     */
    Integer submitBatch(@Param("ids") String[] ids, @Param("status")int status);
}
