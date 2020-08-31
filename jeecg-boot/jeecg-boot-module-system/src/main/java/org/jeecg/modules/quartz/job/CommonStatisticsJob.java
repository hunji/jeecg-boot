package org.jeecg.modules.quartz.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.WeChatUtil;
import org.jeecg.modules.quartz.entity.StatisticsEntity;
import org.jeecg.modules.quartz.service.IStatisticsService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 通用的查询统计任务
 * 根据sql语句查出内容，定时使用微信发送
 * @author hunji
 * @version 1.0
 * @date 2020-7-6 14:55
 */
@Slf4j
public class CommonStatisticsJob implements Job {

    @Autowired
    private IStatisticsService service;
    /**
     * 传入参数是StatisticsEntity的name
     */
    private String parameter;

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 1.根据名称查找任务
        //   名称唯一，只能查找到唯一一个查询统计
        StatisticsEntity entity = service.getOne(new QueryWrapper<StatisticsEntity>().eq("name", parameter));
        // 2.执行slq，拼接查询结果（发送信息参照模板）
        List<LinkedHashMap> sqlResults = service.queryStatistics(entity.getSqlStr());

        String regex = ";|；|\\s+";
        String[] columNames= entity.getColumNames().split(regex);

        StringBuilder sb=new StringBuilder();
        sb.append("### "+entity.getTitle()+"                                                            \n");
        sb.append("**说明："+entity.getDescription()+"**");
        // 循环拼接 --两种情况，一种只能查出一条数据的；一种为查出多条数据的

        for (LinkedHashMap sqlResult : sqlResults) {
            int i = 0;
            for ( Object item : sqlResult.keySet()) {
                sb.append("\n");
                if(columNames.length>i){
                    sb.append(columNames[i]+":");
                }
                sb.append(sqlResult.get(item));
                i++;
            }
        }
        // 3.调用企业微信工具进行发送信息
        String result = WeChatUtil.sendInfo(entity.getWeChatToUser(), sb.toString());
        log.info(result);
    }
}
