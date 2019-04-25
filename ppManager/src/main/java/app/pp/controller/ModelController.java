package app.pp.controller;

import app.pp.common.Result;
import app.pp.entity.Model;
import app.pp.enums.ErrorEnum;
import app.pp.service.ModelService;
import app.pp.utils.ResultUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
  保单模版相关接口
 */
@RestController
@RequestMapping("/model")
public class ModelController {

    @Autowired
    ModelService modelService;

    /**
     * 新增保单模版
     */
    @PostMapping("/save")
    @RequiresPermissions("sys:model:save")
    public Result saveModel(@RequestBody Model model) {
        return modelService.saveModel(model);
    }

    /**
     * 删除保单模板
     */
    @GetMapping("/del/{id}")
    @RequiresPermissions("sys:model:delete")
    public Result del(@PathVariable(value = "id") Integer id) {
        return modelService.del(id);
    }

    /**
     * 模板禁用启用
     */
    @GetMapping("disoren/{id}/{state}")
    @RequiresPermissions("sys:model:disoren")
    public Result disoren(@PathVariable(value = "id") Integer id, @PathVariable(value = "state") Integer state) {

        return modelService.disoren(id, state);
    }

    /**
     * 模板列表
     */
    @GetMapping("list/{page}")
    @RequiresPermissions("sys:model:list")
    public Result list(@PathVariable(value = "page") Integer page) {
        return modelService.list(page);
    }

    /**
     * 模板下拉框
     *
     * @return
     */
    @GetMapping("select")
    public Result select() {
        return ResultUtils.result(ErrorEnum.SUCCESS, modelService.select());
    }
}
