package org.jeecg.modules.quartz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.quartz.entity.StatisticsEntity;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author hunji
 * @version 1.0
 * @date 2020-7-6 12:27
 */
public interface StatisticsMapper extends BaseMapper<StatisticsEntity> {
    /**
     * 公共的统计查询方法
     * @param sql
     * @return 有序的字段
     */
    List<LinkedHashMap> queryStatistics(String sql);
}
