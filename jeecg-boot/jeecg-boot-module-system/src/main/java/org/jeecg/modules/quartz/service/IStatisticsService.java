package org.jeecg.modules.quartz.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.jeecg.common.system.base.service.JeecgService;
import org.jeecg.modules.quartz.entity.StatisticsEntity;

import java.util.LinkedHashMap;

/**
 * 公共的查询统计方法，接受后台传入的sql语句进行查
 * @author hunji
 * @version 1.0
 * @date 2020-7-6 12:29
 */
public interface IStatisticsService extends JeecgService<StatisticsEntity> {
    /**
     * 按照顺序获取动态传入sql语句得到的结果列
     * @return
     */
    @DS("newmes")
    LinkedHashMap queryStatistics(String sql);
}
