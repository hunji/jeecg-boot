package org.jeecg.modules.om.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.om.entity.ZqQuestionType;
import org.jeecg.modules.om.service.IZqQuestionTypeService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 问题类型
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
@Api(tags="问题类型")
@RestController
@RequestMapping("/om/zqQuestionType")
@Slf4j
public class ZqQuestionTypeController extends JeecgController<ZqQuestionType, IZqQuestionTypeService>{
	@Autowired
	private IZqQuestionTypeService zqQuestionTypeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param zqQuestionType
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "问题类型-分页列表查询")
	@ApiOperation(value="问题类型-分页列表查询", notes="问题类型-分页列表查询")
	@GetMapping(value = "/rootList")
	public Result<?> queryPageList(ZqQuestionType zqQuestionType,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		String parentId = zqQuestionType.getPid();
		if (oConvertUtils.isEmpty(parentId)) {
			parentId = "0";
		}
		zqQuestionType.setPid(null);
		QueryWrapper<ZqQuestionType> queryWrapper = QueryGenerator.initQueryWrapper(zqQuestionType, req.getParameterMap());
		// 使用 eq 防止模糊查询
		queryWrapper.eq("pid", parentId);
		Page<ZqQuestionType> page = new Page<ZqQuestionType>(pageNo, pageSize);
		IPage<ZqQuestionType> pageList = zqQuestionTypeService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	 /**
      * 获取子数据
      * @param testTree
      * @param req
      * @return
      */
	@AutoLog(value = "问题类型-获取子数据")
	@ApiOperation(value="问题类型-获取子数据", notes="问题类型-获取子数据")
	@GetMapping(value = "/childList")
	public Result<?> queryPageList(ZqQuestionType zqQuestionType,HttpServletRequest req) {
		QueryWrapper<ZqQuestionType> queryWrapper = QueryGenerator.initQueryWrapper(zqQuestionType, req.getParameterMap());
		List<ZqQuestionType> list = zqQuestionTypeService.list(queryWrapper);
		return Result.ok(list);
	}
	
	
	/**
	 *   添加
	 *
	 * @param zqQuestionType
	 * @return
	 */
	@AutoLog(value = "问题类型-添加")
	@ApiOperation(value="问题类型-添加", notes="问题类型-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ZqQuestionType zqQuestionType) {
		zqQuestionTypeService.addZqQuestionType(zqQuestionType);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param zqQuestionType
	 * @return
	 */
	@AutoLog(value = "问题类型-编辑")
	@ApiOperation(value="问题类型-编辑", notes="问题类型-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ZqQuestionType zqQuestionType) {
		zqQuestionTypeService.updateZqQuestionType(zqQuestionType);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "问题类型-通过id删除")
	@ApiOperation(value="问题类型-通过id删除", notes="问题类型-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		zqQuestionTypeService.deleteZqQuestionType(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "问题类型-批量删除")
	@ApiOperation(value="问题类型-批量删除", notes="问题类型-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.zqQuestionTypeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "问题类型-通过id查询")
	@ApiOperation(value="问题类型-通过id查询", notes="问题类型-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ZqQuestionType zqQuestionType = zqQuestionTypeService.getById(id);
		if(zqQuestionType==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(zqQuestionType);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param zqQuestionType
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ZqQuestionType zqQuestionType) {
		return super.exportXls(request, zqQuestionType, ZqQuestionType.class, "问题类型");
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
		return super.importExcel(request, response, ZqQuestionType.class);
    }

}
