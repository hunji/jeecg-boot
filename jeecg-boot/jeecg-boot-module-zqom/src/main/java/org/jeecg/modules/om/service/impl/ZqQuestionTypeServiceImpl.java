package org.jeecg.modules.om.service.impl;

import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.om.entity.ZqQuestionType;
import org.jeecg.modules.om.mapper.ZqQuestionTypeMapper;
import org.jeecg.modules.om.service.IZqQuestionTypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 问题类型
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
@Service
public class ZqQuestionTypeServiceImpl extends ServiceImpl<ZqQuestionTypeMapper, ZqQuestionType> implements IZqQuestionTypeService {

	@Override
	public void addZqQuestionType(ZqQuestionType zqQuestionType) {
		if(oConvertUtils.isEmpty(zqQuestionType.getPid())){
			zqQuestionType.setPid(IZqQuestionTypeService.ROOT_PID_VALUE);
		}else{
			//如果当前节点父ID不为空 则设置父节点的hasChildren 为1
			ZqQuestionType parent = baseMapper.selectById(zqQuestionType.getPid());
			if(parent!=null && !"1".equals(parent.getHasChild())){
				parent.setHasChild("1");
				baseMapper.updateById(parent);
			}
		}
		baseMapper.insert(zqQuestionType);
	}
	
	@Override
	public void updateZqQuestionType(ZqQuestionType zqQuestionType) {
		ZqQuestionType entity = this.getById(zqQuestionType.getId());
		if(entity==null) {
			throw new JeecgBootException("未找到对应实体");
		}
		String old_pid = entity.getPid();
		String new_pid = zqQuestionType.getPid();
		if(!old_pid.equals(new_pid)) {
			updateOldParentNode(old_pid);
			if(oConvertUtils.isEmpty(new_pid)){
				zqQuestionType.setPid(IZqQuestionTypeService.ROOT_PID_VALUE);
			}
			if(!IZqQuestionTypeService.ROOT_PID_VALUE.equals(zqQuestionType.getPid())) {
				baseMapper.updateTreeNodeStatus(zqQuestionType.getPid(), IZqQuestionTypeService.HASCHILD);
			}
		}
		baseMapper.updateById(zqQuestionType);
	}
	
	@Override
	public void deleteZqQuestionType(String id) throws JeecgBootException {
		ZqQuestionType zqQuestionType = this.getById(id);
		if(zqQuestionType==null) {
			throw new JeecgBootException("未找到对应实体");
		}
		updateOldParentNode(zqQuestionType.getPid());
		baseMapper.deleteById(id);
	}
	
	
	
	/**
	 * 根据所传pid查询旧的父级节点的子节点并修改相应状态值
	 * @param pid
	 */
	private void updateOldParentNode(String pid) {
		if(!IZqQuestionTypeService.ROOT_PID_VALUE.equals(pid)) {
			Integer count = baseMapper.selectCount(new QueryWrapper<ZqQuestionType>().eq("pid", pid));
			if(count==null || count<=1) {
				baseMapper.updateTreeNodeStatus(pid, IZqQuestionTypeService.NOCHILD);
			}
		}
	}

}
