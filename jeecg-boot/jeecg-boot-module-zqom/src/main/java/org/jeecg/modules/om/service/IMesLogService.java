package org.jeecg.modules.om.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.system.base.service.JeecgService;
import org.jeecg.modules.om.entity.MesLogEntity;

/**
 * @author hunji
 * @version 1.0
 * @date 2020-2-27 21:08
 */
public interface IMesLogService extends JeecgService<MesLogEntity> {
    /**
     * 分页获取Mes系统的日志
     * @param page
     * @param logType
     * @return
     */
    IPage<MesLogEntity> queryLogInfo(Page<MesLogEntity> page, @Param("logType")String logType
        , @Param("moduleId")String moduleId, @Param("functionId")String functionId);
}
