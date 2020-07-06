package org.jeecg.modules.quartz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hunji
 * @version 1.0
 * @date 2020-7-6 12:24
 */
@Data
@TableName("sys_statistics")
public class StatisticsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**主键*/
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 标题
     */
    private String title;

    /**
     * 说明
     */
    private String description;

    /**
     * 接收人
     * 格式如: 0196;0118;0441;0293
     */
    private String weChatToUser;

    /**
     * 执行sql
     */
    private String sqlStr;

    /**
     * 预留的字段字符串 ，已中英文逗号或者空格隔开
     * 如：查询日期，腕带关联数量
     * String regex = "；|;|\\s+";
     * String strAry[] = str.split(regex);
     *
     */
    private String columNames;
}
