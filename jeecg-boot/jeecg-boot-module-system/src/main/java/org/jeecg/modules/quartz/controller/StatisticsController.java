package org.jeecg.modules.quartz.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.quartz.entity.StatisticsEntity;
import org.jeecg.modules.quartz.service.IStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author hunji
 * @version 1.0
 * @date 2020-7-6 15:15
 */
@Slf4j
@RestController
@RequestMapping("/sys/Statistics")
public class StatisticsController extends JeecgController<StatisticsEntity, IStatisticsService> {
    @Autowired
    private IStatisticsService statisticsService;

    @GetMapping(value = "/list")
    public Result<?> list(StatisticsEntity entity, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                          HttpServletRequest req) {
        QueryWrapper<StatisticsEntity> queryWrapper = QueryGenerator.initQueryWrapper(entity, req.getParameterMap());
        Page<StatisticsEntity> page = new Page<StatisticsEntity>(pageNo, pageSize);

        IPage<StatisticsEntity> pageList = statisticsService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param entity
     * @return
     */
    @PostMapping(value = "/add")
    @AutoLog(value = "添加sql统计查询实体")
    public Result<?> add(@RequestBody StatisticsEntity entity) {
        int countName = statisticsService.count(new QueryWrapper<StatisticsEntity>().eq("name", entity.getName()));
        if(countName>0){
            throw  new JeecgBootException("不可命名同名的查询统计"+entity.getName());
        }
        statisticsService.save(entity);
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param entity
     * @return
     */
    @PutMapping(value = "/edit")
    @AutoLog(value = "编辑sql统计查询实体", operateType = CommonConstant.OPERATE_TYPE_3)
    public Result<?> edit(@RequestBody StatisticsEntity entity) {
        statisticsService.updateById(entity);
        return Result.ok("更新成功！");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "删除sql统计查询")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        statisticsService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.statisticsService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@ApiParam(name = "id", value = "示例id", required = true) @RequestParam(name = "id", required = true) String id) {
        StatisticsEntity entity = statisticsService.getById(id);
        return Result.ok(entity);
    }
}
