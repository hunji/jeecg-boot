package org.jeecg.modules.om.service;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.om.entity.ZqQuestionContent;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 日常问题
 * @Author: jeecg-boot
 * @Date:   2020-07-30
 * @Version: V1.0
 */
public interface IZqQuestionContentService extends IService<ZqQuestionContent> {
    /**
     * 批量提交
     * @param ids
     * @param status
     * @return
     */
    Integer submitBatch(@Param("ids") String[] ids, @Param("status")int status);
}
