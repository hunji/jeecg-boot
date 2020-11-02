package org.jeecg.modules.om.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.PermissionData;
import org.jeecg.modules.om.entity.MDCRecordEntity;
import org.jeecg.modules.om.entity.MesLogEntity;
import org.jeecg.modules.om.service.IMdcRecordService;
import org.jeecg.modules.om.service.IMesLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author hunji
 * @version 1.0
 * @date 2020-11-2 10:50
 */
@Slf4j
@RestController
@RequestMapping("/om/mdcRecord")
public class MdcRecordController {

    @Autowired
    private IMdcRecordService MdcRecordService;

    @GetMapping(value = "/list")
    public Result<?> list(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
            , @RequestParam(name = "lotno") String lotno, @RequestParam(name = "machineCode") String machineCode
            , @RequestParam(name = "kssj") String kssj, @RequestParam(name = "jssj") String jssj, HttpServletRequest req) {

        ArrayList<MDCRecordEntity> data = MdcRecordService.queryMdcRecordInfo(lotno, machineCode,kssj,jssj);
        return Result.ok(data);
    }
}
