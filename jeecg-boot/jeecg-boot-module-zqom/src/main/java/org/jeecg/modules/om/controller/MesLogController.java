package org.jeecg.modules.om.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.PermissionData;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.om.entity.MesErrorEntity;
import org.jeecg.modules.om.entity.MesLogEntity;
import org.jeecg.modules.om.entity.MesLogXmlEntity;
import org.jeecg.modules.om.service.IMesErrorService;
import org.jeecg.modules.om.service.IMesLogService;
import org.jeecg.modules.om.service.IMesLogXmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hunji
 * @version 1.0
 * @date 2020-7-6 16:40
 */
@Slf4j
@Api(tags = "MES日志查询")
@RestController
@RequestMapping("/om/mesLog")
public class MesLogController  extends JeecgController<MesLogEntity, IMesLogService> {
    @Autowired
    private IMesLogService mesLogService;
    @Autowired
    private IMesLogXmlService mesLogXmlService;
    @Autowired
    private IMesErrorService mesErrorService;

    @ApiOperation(value = "获取mes日志列表", notes = "获取mes日志列表")
    @GetMapping(value = "/list")
    @PermissionData(pageComponent = "modules/monitor/MesLogInfo")
    public Result<?> list(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
            , @RequestParam(name = "logType", defaultValue = "0") String logType, @RequestParam(name = "moduleId") String moduleId
            , @RequestParam(name = "functionId") String functionId, HttpServletRequest req) {

        Page<MesLogEntity> page = new Page<>(pageNo, pageSize);
        IPage<MesLogEntity> data = mesLogService.queryLogInfo(page, logType,moduleId,functionId);
        return Result.ok(data);
    }

    @ApiOperation(value = "获取mes异常日志列表", notes = "获取mes异常日志列表")
    @GetMapping(value = "/xmllist")
    public Result<?> xmlList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
            ,HttpServletRequest req) {

        Page<MesLogXmlEntity> page = new Page<>(pageNo, pageSize);
        IPage<MesLogXmlEntity> data = mesLogXmlService.page(page);
        return Result.ok(data);
    }

    @ApiOperation(value = "获取mes异常日志列表", notes = "获取mes异常日志列表")
    @GetMapping(value = "/xmlInfo")
    public Result<?> xmlInfo(@RequestParam(name = "id") String id){
        MesLogXmlEntity xmlInfo = mesLogXmlService.getById(id);
        return Result.ok(xmlInfo);
    }

    @ApiOperation(value = "获取mesError列表", notes = "获取mes异常日志列表")
    @GetMapping(value = "/errorlist")
    public Result<?> errorList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
            , @RequestParam(name = "serviceName") String serviceName, @RequestParam(name = "userName") String userName){
        Page<MesErrorEntity> page = new Page<>(pageNo, pageSize);
        // .eq(StringUtils.isNotBlank(enable),"enable",enable)
        QueryWrapper<MesErrorEntity> queryWrapper =new QueryWrapper<MesErrorEntity>()
                .eq(StringUtils.isNotBlank(serviceName),"servicename",serviceName)
                .eq(StringUtils.isNotBlank(userName),"userid",userName)
                .orderByDesc("recorddate");
        IPage<MesErrorEntity> data = mesErrorService.page(page,queryWrapper);
        return Result.ok(data);
    }
}
