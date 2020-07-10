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
import org.jeecg.modules.om.entity.ZqQuestionContent;
import org.jeecg.modules.om.service.IZqQuestionContentService;

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
 * @Description: 日常问题
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
@Api(tags="日常问题")
@RestController
@RequestMapping("/om/zqQuestionContent")
@Slf4j
public class ZqQuestionContentController extends JeecgController<ZqQuestionContent, IZqQuestionContentService> {
	@Autowired
	private IZqQuestionContentService zqQuestionContentService;
	
	/**
	 * 分页列表查询
	 *
	 * @param zqQuestionContent
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "日常问题-分页列表查询")
	@ApiOperation(value="日常问题-分页列表查询", notes="日常问题-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ZqQuestionContent zqQuestionContent,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ZqQuestionContent> queryWrapper = QueryGenerator.initQueryWrapper(zqQuestionContent, req.getParameterMap());
		Page<ZqQuestionContent> page = new Page<ZqQuestionContent>(pageNo, pageSize);
		IPage<ZqQuestionContent> pageList = zqQuestionContentService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param zqQuestionContent
	 * @return
	 */
	@AutoLog(value = "日常问题-添加")
	@ApiOperation(value="日常问题-添加", notes="日常问题-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ZqQuestionContent zqQuestionContent) {
		zqQuestionContentService.save(zqQuestionContent);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param zqQuestionContent
	 * @return
	 */
	@AutoLog(value = "日常问题-编辑")
	@ApiOperation(value="日常问题-编辑", notes="日常问题-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ZqQuestionContent zqQuestionContent) {
		zqQuestionContentService.updateById(zqQuestionContent);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "日常问题-通过id删除")
	@ApiOperation(value="日常问题-通过id删除", notes="日常问题-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		zqQuestionContentService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "日常问题-批量删除")
	@ApiOperation(value="日常问题-批量删除", notes="日常问题-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.zqQuestionContentService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "日常问题-通过id查询")
	@ApiOperation(value="日常问题-通过id查询", notes="日常问题-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ZqQuestionContent zqQuestionContent = zqQuestionContentService.getById(id);
		if(zqQuestionContent==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(zqQuestionContent);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param zqQuestionContent
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ZqQuestionContent zqQuestionContent) {
        return super.exportXls(request, zqQuestionContent, ZqQuestionContent.class, "日常问题");
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
        return super.importExcel(request, response, ZqQuestionContent.class);
    }

}
