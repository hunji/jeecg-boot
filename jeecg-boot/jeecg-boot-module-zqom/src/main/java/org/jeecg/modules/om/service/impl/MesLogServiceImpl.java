package org.jeecg.modules.om.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.om.entity.MesLogEntity;
import org.jeecg.modules.om.mapper.MesLogMapper;
import org.jeecg.modules.om.service.IMesLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hunji
 * @version 1.0
 * @date 2020-2-27 21:09
 */
@DS("newmes")
@Service
public class MesLogServiceImpl extends ServiceImpl<MesLogMapper, MesLogEntity> implements IMesLogService {
    @Autowired
    private MesLogMapper mesLogMapper;

    @Override
    public IPage<MesLogEntity> queryLogInfo(Page<MesLogEntity> page, String logType, String moduleId, String functionId) {
        return mesLogMapper.queryLogInfo(page,logType,moduleId,functionId);
    }
}
