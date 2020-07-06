package org.jeecg.modules.om.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.om.entity.MesLogXmlEntity;
import org.jeecg.modules.om.mapper.MesLogXmlMapper;
import org.jeecg.modules.om.service.IMesLogXmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hunji
 * @version 1.0
 * @date 2020-2-28 14:30
 */
@DS("newmes")
@Service
public class MesLogXmlServiceImpl extends ServiceImpl<MesLogXmlMapper, MesLogXmlEntity> implements IMesLogXmlService {
    @Autowired
    private MesLogXmlMapper logXmlMapper;
}
