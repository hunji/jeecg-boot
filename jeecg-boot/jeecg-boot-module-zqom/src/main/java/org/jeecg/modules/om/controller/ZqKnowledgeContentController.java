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

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.om.entity.ZqKnowledgeContent;
import org.jeecg.modules.om.service.IZqKnowledgeContentService;

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
 * @Description: 知识内容信息
 * @Author: jeecg-boot
 * @Date:   2020-07-09
 * @Version: V1.0
 */
@Api(tags="知识内容信息")
@RestController
@RequestMapping("/om/zqKnowledgeContent")
@Slf4j
public class ZqKnowledgeContentController extends JeecgController<ZqKnowledgeContent, IZqKnowledgeContentService> {
	@Autowired
	private IZqKnowledgeContentService zqKnowledgeContentService;
	
	/**
	 * 分页列表查询
	 *
	 * @param zqKnowledgeContent
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "知识内容信息-分页列表查询")
	@ApiOperation(value="知识内容信息-分页列表查询", notes="知识内容信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ZqKnowledgeContent zqKnowledgeContent,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ZqKnowledgeContent> queryWrapper = QueryGenerator.initQueryWrapper(zqKnowledgeContent, req.getParameterMap());
		Page<ZqKnowledgeContent> page = new Page<ZqKnowledgeContent>(pageNo, pageSize);
		IPage<ZqKnowledgeContent> pageList = zqKnowledgeContentService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param zqKnowledgeContent
	 * @return
	 */
	@AutoLog(value = "知识内容信息-添加")
	@ApiOperation(value="知识内容信息-添加", notes="知识内容信息-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ZqKnowledgeContent zqKnowledgeContent) {
		zqKnowledgeContentService.save(zqKnowledgeContent);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param zqKnowledgeContent
	 * @return
	 */
	@AutoLog(value = "知识内容信息-编辑")
	@ApiOperation(value="知识内容信息-编辑", notes="知识内容信息-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ZqKnowledgeContent zqKnowledgeContent) {
		zqKnowledgeContentService.updateById(zqKnowledgeContent);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "知识内容信息-通过id删除")
	@ApiOperation(value="知识内容信息-通过id删除", notes="知识内容信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		zqKnowledgeContentService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "知识内容信息-批量删除")
	@ApiOperation(value="知识内容信息-批量删除", notes="知识内容信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.zqKnowledgeContentService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "知识内容信息-通过id查询")
	@ApiOperation(value="知识内容信息-通过id查询", notes="知识内容信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ZqKnowledgeContent zqKnowledgeContent = zqKnowledgeContentService.getById(id);
		if(zqKnowledgeContent==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(zqKnowledgeContent);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param zqKnowledgeContent
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ZqKnowledgeContent zqKnowledgeContent) {
        return super.exportXls(request, zqKnowledgeContent, ZqKnowledgeContent.class, "知识内容信息");
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
        return super.importExcel(request, response, ZqKnowledgeContent.class);
    }

	 /**
	  *  撤销审核
	  */
	 @RequiresPermissions("knowledge:review")
	 @AutoLog(value = "")
	 @ApiOperation(value="知识内容信息-撤销审核", notes="知识内容信息-撤销审核")
	 @PutMapping(value = "/sendBack")
	 public Result<?> sendBack(@RequestBody JSONObject jsonObject) {
		 String ids = jsonObject.getString("ids");
		 this.zqKnowledgeContentService.submitBatch(ids.split(","),0);
		 return Result.ok("撤销成功!");
	 }

	 /**
	  *  审核通过
	  */
	 @RequiresPermissions("knowledge:review")
	 @AutoLog(value = "知识内容信息-审核通过")
	 @ApiOperation(value="知识内容信息-审核通过", notes="知识内容信息-审核通过")
	 @PutMapping(value = "/review")
	 public Result<?> review(@RequestBody JSONObject jsonObject) {
		 String ids = jsonObject.getString("ids");
		 this.zqKnowledgeContentService.submitBatch(ids.split(","),1);
		 return Result.ok("审核通过!");
	 }
}
