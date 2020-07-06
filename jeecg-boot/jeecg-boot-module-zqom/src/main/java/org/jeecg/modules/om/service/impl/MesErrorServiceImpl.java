package org.jeecg.modules.om.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.om.entity.MesErrorEntity;
import org.jeecg.modules.om.mapper.MesErrorMapper;
import org.jeecg.modules.om.service.IMesErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hunji
 * @version 1.0
 * @date 2020-2-28 15:33
 */
@DS("newmes")
@Service
public class MesErrorServiceImpl extends ServiceImpl<MesErrorMapper, MesErrorEntity> implements IMesErrorService {
    @Autowired
    private MesErrorMapper mesErrorMapper;
}
