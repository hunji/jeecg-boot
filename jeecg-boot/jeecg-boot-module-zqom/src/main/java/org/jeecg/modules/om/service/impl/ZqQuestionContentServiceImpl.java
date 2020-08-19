package org.jeecg.modules.om.service.impl;

import org.jeecg.modules.om.entity.ZqQuestionContent;
import org.jeecg.modules.om.mapper.MesLogXmlMapper;
import org.jeecg.modules.om.mapper.ZqKnowledgeContentMapper;
import org.jeecg.modules.om.mapper.ZqQuestionContentMapper;
import org.jeecg.modules.om.service.IZqQuestionContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 日常问题
 * @Author: jeecg-boot
 * @Date:   2020-07-30
 * @Version: V1.0
 */
@Service
public class ZqQuestionContentServiceImpl extends ServiceImpl<ZqQuestionContentMapper, ZqQuestionContent> implements IZqQuestionContentService {
    @Autowired
    private ZqQuestionContentMapper mapper;

    @Override
    public Integer submitBatch(String[] ids,int status) {
        return mapper.submitBatch(ids,status);
    }
}
