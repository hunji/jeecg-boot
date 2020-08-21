package org.jeecg.modules.om.service.impl;

import org.jeecg.modules.om.entity.ZqKnowledgeContent;
import org.jeecg.modules.om.entity.ZqQuestionContent;
import org.jeecg.modules.om.mapper.MesLogXmlMapper;
import org.jeecg.modules.om.mapper.ZqKnowledgeContentMapper;
import org.jeecg.modules.om.mapper.ZqQuestionContentMapper;
import org.jeecg.modules.om.service.IZqKnowledgeContentService;
import org.jeecg.modules.om.service.IZqQuestionContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private IZqKnowledgeContentService knowledgeContentService;

    @Override
    public Integer submitBatch(String[] ids,int status) {
        return mapper.submitBatch(ids,status);
    }

    @Override
    public void transformQuestionToKnowledge(List<ZqQuestionContent> list) {
        ArrayList<ZqKnowledgeContent> knowledgeContents = new ArrayList<>();
        for (ZqQuestionContent question : list) {
            ZqKnowledgeContent knowledgeContent = new ZqKnowledgeContent();
            knowledgeContent.setTitle("问题整理");
            knowledgeContent.setBrief(question.getBrief());
            // TODO:此处默认类型为现场问题，代码写死
            knowledgeContent.setTypeId("cfa6d67f4d1af8c8b9140ea68f838f10");
            knowledgeContent.setContent(question.getContent() + "<p>解决方案：</p>" + question.getSolution());
            knowledgeContents.add(knowledgeContent);
        }
        knowledgeContentService.saveBatch(knowledgeContents);
    }
}
