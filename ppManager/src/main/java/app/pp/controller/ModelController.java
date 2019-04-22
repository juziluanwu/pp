package app.pp.controller;

import app.pp.common.Result;
import app.pp.entity.Model;
import app.pp.service.ModelService;
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
     *新增保单模版
     */
    @PostMapping("/save")
    public Result saveModel(@RequestBody Model model){
        return modelService.saveModel(model);
    }

    /**
     * 删除保单模板
     */
    @GetMapping("/del/{id}")
    public Result del(@PathVariable(value = "id") Integer id){
        return modelService.del(id);
    }
    /**
     * 模板禁用启用
     */
    @GetMapping("disoren/{id}/{state}")
    public Result disoren(@PathVariable(value = "id") Integer id,@PathVariable(value = "state") Integer state){

        return modelService.disoren(id,state);
    }
    /**
     * 模板列表
     */
    @GetMapping("list")
    public Result list(){
        return modelService.list();
    }
}
