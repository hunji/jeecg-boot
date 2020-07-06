package org.jeecg.modules.om.service.impl;

import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.om.entity.ZqKnowledgeType;
import org.jeecg.modules.om.mapper.ZqKnowledgeTypeMapper;
import org.jeecg.modules.om.service.IZqKnowledgeTypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 知识库类型
 * @Author: jeecg-boot
 * @Date:   2020-07-05
 * @Version: V1.0
 */
@Service
public class ZqKnowledgeTypeServiceImpl extends ServiceImpl<ZqKnowledgeTypeMapper, ZqKnowledgeType> implements IZqKnowledgeTypeService {

	@Override
	public void addZqKnowledgeType(ZqKnowledgeType zqKnowledgeType) {
		if(oConvertUtils.isEmpty(zqKnowledgeType.getPid())){
			zqKnowledgeType.setPid(IZqKnowledgeTypeService.ROOT_PID_VALUE);
		}else{
			//如果当前节点父ID不为空 则设置父节点的hasChildren 为1
			ZqKnowledgeType parent = baseMapper.selectById(zqKnowledgeType.getPid());
			if(parent!=null && !"1".equals(parent.getHasChild())){
				parent.setHasChild("1");
				baseMapper.updateById(parent);
			}
		}
		baseMapper.insert(zqKnowledgeType);
	}
	
	@Override
	public void updateZqKnowledgeType(ZqKnowledgeType zqKnowledgeType) {
		ZqKnowledgeType entity = this.getById(zqKnowledgeType.getId());
		if(entity==null) {
			throw new JeecgBootException("未找到对应实体");
		}
		String old_pid = entity.getPid();
		String new_pid = zqKnowledgeType.getPid();
		if(!old_pid.equals(new_pid)) {
			updateOldParentNode(old_pid);
			if(oConvertUtils.isEmpty(new_pid)){
				zqKnowledgeType.setPid(IZqKnowledgeTypeService.ROOT_PID_VALUE);
			}
			if(!IZqKnowledgeTypeService.ROOT_PID_VALUE.equals(zqKnowledgeType.getPid())) {
				baseMapper.updateTreeNodeStatus(zqKnowledgeType.getPid(), IZqKnowledgeTypeService.HASCHILD);
			}
		}
		baseMapper.updateById(zqKnowledgeType);
	}
	
	@Override
	public void deleteZqKnowledgeType(String id) throws JeecgBootException {
		ZqKnowledgeType zqKnowledgeType = this.getById(id);
		if(zqKnowledgeType==null) {
			throw new JeecgBootException("未找到对应实体");
		}
		updateOldParentNode(zqKnowledgeType.getPid());
		baseMapper.deleteById(id);
	}
	
	
	
	/**
	 * 根据所传pid查询旧的父级节点的子节点并修改相应状态值
	 * @param pid
	 */
	private void updateOldParentNode(String pid) {
		if(!IZqKnowledgeTypeService.ROOT_PID_VALUE.equals(pid)) {
			Integer count = baseMapper.selectCount(new QueryWrapper<ZqKnowledgeType>().eq("pid", pid));
			if(count==null || count<=1) {
				baseMapper.updateTreeNodeStatus(pid, IZqKnowledgeTypeService.NOCHILD);
			}
		}
	}

}
