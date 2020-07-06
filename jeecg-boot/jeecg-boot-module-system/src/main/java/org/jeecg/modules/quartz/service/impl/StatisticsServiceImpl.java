package org.jeecg.modules.quartz.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.quartz.entity.StatisticsEntity;
import org.jeecg.modules.quartz.mapper.StatisticsMapper;
import org.jeecg.modules.quartz.service.IStatisticsService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author hunji
 * @version 1.0
 * @date 2020-7-6 14:54
 */
@Service
public class StatisticsServiceImpl extends ServiceImpl<StatisticsMapper, StatisticsEntity> implements IStatisticsService {

    @DS("newmes")
    @Override
    public LinkedHashMap queryStatistics(String sql) {
        return baseMapper.queryStatistics(sql);
    }
}
