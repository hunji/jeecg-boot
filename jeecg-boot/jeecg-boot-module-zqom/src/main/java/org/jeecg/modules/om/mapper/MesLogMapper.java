package org.jeecg.modules.om.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.om.entity.MesLogEntity;

/**
 * @author hunji
 * @version 1.0
 * @date 2020-7-6 16:41
 */
public interface MesLogMapper extends BaseMapper<MesLogEntity> {
    /**
     * 分页查找日志
     * @param page
     * @param logType
     * @return
     */
    IPage<MesLogEntity> queryLogInfo(Page<MesLogEntity> page, @Param("logType")String logType, @Param("moduleId")String moduleId, @Param("functionId")String functionId);
}