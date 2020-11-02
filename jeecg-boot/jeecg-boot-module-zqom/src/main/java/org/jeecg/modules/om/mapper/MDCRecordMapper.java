package org.jeecg.modules.om.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.om.entity.MDCRecordEntity;
import org.jeecg.modules.om.entity.MesLogEntity;

import java.util.ArrayList;

/**
 * @author hunji
 * @version 1.0
 * @date 2020-11-2 10:34
 */
public interface MDCRecordMapper extends BaseMapper<MDCRecordEntity> {
    ArrayList<MDCRecordEntity> queryMdcRecordInfo(String lotno, String machineCode,String kssj,String jssj);
}
