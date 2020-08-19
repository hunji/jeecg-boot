package org.jeecg.modules.om.service.impl;

import org.jeecg.modules.om.entity.ZqKnowledgeContent;
import org.jeecg.modules.om.mapper.ZqKnowledgeContentMapper;
import org.jeecg.modules.om.service.IZqKnowledgeContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 知识内容信息
 * @Author: jeecg-boot
 * @Date:   2020-07-09
 * @Version: V1.0
 */
@Service
public class ZqKnowledgeContentServiceImpl extends ServiceImpl<ZqKnowledgeContentMapper, ZqKnowledgeContent> implements IZqKnowledgeContentService {

    @Autowired
    private ZqKnowledgeContentMapper mapper;
    @Override
    public Integer submitBatch(String[] ids, int status) {
        return mapper.submitBatch(ids,status);
    }
}
