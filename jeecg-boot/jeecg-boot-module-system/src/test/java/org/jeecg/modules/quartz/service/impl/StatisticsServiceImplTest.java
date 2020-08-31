package org.jeecg.modules.quartz.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.quartz.service.IStatisticsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author hunji
 * @version 1.0
 * @date 2020-7-6 15:19
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatisticsServiceImplTest {
    @Autowired
    private IStatisticsService service;

    @Test
    public void queryStatistics() {
        String sql="SELECT SJ AS CXSJ,SUM(W1) AS DMGGLSL, SUM(S6) AS CLJCHSSL,SUM(S1) AS BZHSSL,SUM(S2)  AS YZJHSSL ,SUM(S3) AS CLZZZYSL ,SUM(S4) AS PQCLZYSL  ,SUM(S5) AS ZDPCLZYSL from (\n" +
                "  select to_char(sysdate-1,'yyyy/mm/dd') ||'-' || to_char(sysdate,'yyyy/mm/dd')  AS SJ,COUNT(*) AS W1,0 AS S6,0 AS S1 ,0 AS S2  ,0 AS S3 ,0 AS S4  ,0 AS S5\n" +
                "  from TBLRFIDREPLACELOG_ZQRAIL where opno ='W040' and flag = 'INS'\n" +
                "  AND DATETIME between to_date(to_char(sysdate-1,'yyyymmdd')||'08:30:00','yyyymmdd hh24:mi:ss')\n" +
                " and to_date(to_char(sysdate,'yyyymmdd')||'08:30:00','yyyymmdd hh24:mi:ss')\n" +
                "UNION ALL\n" +
                "  select to_char(sysdate-1,'yyyy/mm/dd') ||'-' || to_char(sysdate,'yyyy/mm/dd')  AS SJ,0 AS W1,0 AS S6 ,COUNT(*) AS S1,0 AS S2   ,0 AS S3 ,0 AS S4  ,0 AS S5\n" +
                "   from TBLRFIDREPLACELOG_ZQRAIL where opno ='S220' and flag = 'DEL'\n" +
                "     AND DATETIME between to_date(to_char(sysdate-1,'yyyymmdd')||'08:30:00','yyyymmdd hh24:mi:ss')\n" +
                " and to_date(to_char(sysdate,'yyyymmdd')||'08:30:00','yyyymmdd hh24:mi:ss')\n" +
                " UNION ALL\n" +
                "  select to_char(sysdate-1,'yyyy/mm/dd') ||'-' || to_char(sysdate,'yyyy/mm/dd')  AS SJ,0 AS W1,COUNT(*) AS S6 ,0 AS S1,0 AS S2   ,0 AS S3 ,0 AS S4  ,0 AS S5\n" +
                "   from TBLRFIDREPLACELOG_ZQRAIL where opno ='W100' and flag = 'DEL'\n" +
                "     AND DATETIME between to_date(to_char(sysdate-1,'yyyymmdd')||'08:30:00','yyyymmdd hh24:mi:ss')\n" +
                " and to_date(to_char(sysdate,'yyyymmdd')||'08:30:00','yyyymmdd hh24:mi:ss')\n" +
                "  UNION ALL\n" +
                "  select to_char(sysdate-1,'yyyy/mm/dd') ||'-' || to_char(sysdate,'yyyy/mm/dd')  AS SJ,0 AS W1,0 AS S6 , 0 AS S1,COUNT(*) AS S2  ,0 AS S3 ,0 AS S4  ,0 AS S5\n" +
                "   from TBLRFIDREPLACELOG_ZQRAIL where opno ='S070' and flag = 'DEL'\n" +
                "     AND DATETIME between to_date(to_char(sysdate-1,'yyyymmdd')||'08:30:00','yyyymmdd hh24:mi:ss')\n" +
                " and to_date(to_char(sysdate,'yyyymmdd')||'08:30:00','yyyymmdd hh24:mi:ss')\n" +
                " UNION ALL\n" +
                "  select to_char(sysdate-1,'yyyy/mm/dd') ||'-' || to_char(sysdate,'yyyy/mm/dd')  AS SJ,COUNT(*) AS W1,0 AS S6,0 AS S1 ,0 AS S2  ,0 AS S3 ,0 AS S4  ,0 AS S5\n" +
                "   from  TBLWIPRFIDRELATIONLOG_ZQRAIL T WHERE CREATOR  is null\n" +
                " and CREATEDATE between to_date(to_char(sysdate-1,'yyyymmdd')||'08:30:00','yyyymmdd hh24:mi:ss')\n" +
                " and to_date(to_char(sysdate,'yyyymmdd')||'08:30:00','yyyymmdd hh24:mi:ss')\n" +
                " and lotno like '5103%'\n" +
                " Union All\n" +
                " Select to_char(sysdate-1,'yyyy/mm/dd') ||'-' || to_char(sysdate,'yyyy/mm/dd')  AS SJ,0 AS W1,0 AS S6,0 AS S1 ,0 AS S2 ,Count(*) AS S3 ,0 AS S4  ,0 AS S5\n" +
                "  from tblwiplotstate t, TBLWIPLOTRFIDRELATION_ZQRAIL S\n" +
                "Where Substr(T.LOtno,1,4) = '5103'\n" +
                "And T.LOTNO = S.LOTNO And S.RFIDNO Like '011%'\n" +
                " Union All\n" +
                " Select to_char(sysdate-1,'yyyy/mm/dd') ||'-' || to_char(sysdate,'yyyy/mm/dd')  AS SJ,0 AS W1,0 AS S6,0 AS S1 ,0 AS S2 ,0 AS S3 ,Count(*) AS S4  ,0 AS S5\n" +
                "  from tblwiplotstate t, TBLWIPLOTRFIDRELATION_ZQRAIL S\n" +
                "Where Substr(T.LOtno,1,4) = '5116'\n" +
                "And T.LOTNO = S.LOTNO And S.RFIDNO Like '011%'\n" +
                " Union All\n" +
                " Select to_char(sysdate-1,'yyyy/mm/dd') ||'-' || to_char(sysdate,'yyyy/mm/dd')  AS SJ,0 AS W1,0 AS S6,0 AS S1 ,0 AS S2 ,0 AS S3 ,0 AS S4  ,Count(*) AS S5\n" +
                "  from tblwiplotstate t, TBLWIPLOTRFIDRELATION_ZQRAIL S\n" +
                "Where Substr(T.LOtno,1,4) In( '5117','5212')\n" +
                "And T.LOTNO = S.LOTNO And S.RFIDNO Like '011%'\n" +
                ")\n" +
                "group by SJ";
        List<LinkedHashMap> data = service.queryStatistics(sql);
        for (LinkedHashMap datum : data) {
            for (Object o :datum.keySet()){
                log.info(o.toString() + datum.get(o));
            }
        }
    }
}