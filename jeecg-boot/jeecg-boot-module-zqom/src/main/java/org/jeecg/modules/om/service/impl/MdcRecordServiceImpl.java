package org.jeecg.modules.om.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.om.entity.MDCRecordEntity;
import org.jeecg.modules.om.entity.MesLogEntity;
import org.jeecg.modules.om.mapper.MDCRecordMapper;
import org.jeecg.modules.om.mapper.MesLogMapper;
import org.jeecg.modules.om.service.IMdcRecordService;
import org.jeecg.modules.om.service.IMesLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author hunji
 * @version 1.0
 * @date 2020-11-2 10:47
 */
@DS("newmes")
@Service
public class MdcRecordServiceImpl extends ServiceImpl<MDCRecordMapper, MDCRecordEntity> implements IMdcRecordService {
    @Autowired
    private MDCRecordMapper mapper;

    @Override
    public ArrayList<MDCRecordEntity> queryMdcRecordInfo(String lotno, String machineCode,String kssj,String jssj) {
        return mapper.queryMdcRecordInfo(lotno,machineCode,kssj,jssj);
    }
}
