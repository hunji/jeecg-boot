package org.jeecg.modules.om.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.system.base.service.JeecgService;
import org.jeecg.modules.om.entity.MDCRecordEntity;

import java.util.ArrayList;

/**
 * @author hunji
 * @version 1.0
 * @date 2020-11-2 10:45
 */
public interface IMdcRecordService extends JeecgService<MDCRecordEntity> {
    ArrayList<MDCRecordEntity> queryMdcRecordInfo(String lotno
            , String machineCode,String kssj,String jssj);
}
