package org.jeecg.modules.om.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.om.entity.ZqKnowledgeType;
import org.jeecg.modules.om.service.IZqKnowledgeTypeService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 知识库类型
 * @Author: jeecg-boot
 * @Date:   2020-07-05
 * @Version: V1.0
 */
@Api(tags="知识库类型")
@RestController
@RequestMapping("/om/zqKnowledgeType")
@Slf4j
public class ZqKnowledgeTypeController extends JeecgController<ZqKnowledgeType, IZqKnowledgeTypeService>{
	@Autowired
	private IZqKnowledgeTypeService zqKnowledgeTypeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param zqKnowledgeType
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "知识库类型-分页列表查询")
	@ApiOperation(value="知识库类型-分页列表查询", notes="知识库类型-分页列表查询")
	@GetMapping(value = "/rootList")
	public Result<?> queryPageList(ZqKnowledgeType zqKnowledgeType,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		String parentId = zqKnowledgeType.getPid();
		if (oConvertUtils.isEmpty(parentId)) {
			parentId = "0";
		}
		zqKnowledgeType.setPid(null);
		QueryWrapper<ZqKnowledgeType> queryWrapper = QueryGenerator.initQueryWrapper(zqKnowledgeType, req.getParameterMap());
		// 使用 eq 防止模糊查询
		queryWrapper.eq("pid", parentId);
		Page<ZqKnowledgeType> page = new Page<ZqKnowledgeType>(pageNo, pageSize);
		IPage<ZqKnowledgeType> pageList = zqKnowledgeTypeService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	 /**
      * 获取子数据
      * @param testTree
      * @param req
      * @return
      */
	@AutoLog(value = "知识库类型-获取子数据")
	@ApiOperation(value="知识库类型-获取子数据", notes="知识库类型-获取子数据")
	@GetMapping(value = "/childList")
	public Result<?> queryPageList(ZqKnowledgeType zqKnowledgeType,HttpServletRequest req) {
		QueryWrapper<ZqKnowledgeType> queryWrapper = QueryGenerator.initQueryWrapper(zqKnowledgeType, req.getParameterMap());
		List<ZqKnowledgeType> list = zqKnowledgeTypeService.list(queryWrapper);
		return Result.ok(list);
	}
	
	
	/**
	 *   添加
	 *
	 * @param zqKnowledgeType
	 * @return
	 */
	@AutoLog(value = "知识库类型-添加")
	@ApiOperation(value="知识库类型-添加", notes="知识库类型-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ZqKnowledgeType zqKnowledgeType) {
		zqKnowledgeTypeService.addZqKnowledgeType(zqKnowledgeType);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param zqKnowledgeType
	 * @return
	 */
	@AutoLog(value = "知识库类型-编辑")
	@ApiOperation(value="知识库类型-编辑", notes="知识库类型-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ZqKnowledgeType zqKnowledgeType) {
		zqKnowledgeTypeService.updateZqKnowledgeType(zqKnowledgeType);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "知识库类型-通过id删除")
	@ApiOperation(value="知识库类型-通过id删除", notes="知识库类型-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		zqKnowledgeTypeService.deleteZqKnowledgeType(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "知识库类型-批量删除")
	@ApiOperation(value="知识库类型-批量删除", notes="知识库类型-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.zqKnowledgeTypeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "知识库类型-通过id查询")
	@ApiOperation(value="知识库类型-通过id查询", notes="知识库类型-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ZqKnowledgeType zqKnowledgeType = zqKnowledgeTypeService.getById(id);
		if(zqKnowledgeType==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(zqKnowledgeType);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param zqKnowledgeType
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ZqKnowledgeType zqKnowledgeType) {
		return super.exportXls(request, zqKnowledgeType, ZqKnowledgeType.class, "知识库类型");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
		return super.importExcel(request, response, ZqKnowledgeType.class);
    }

}
