package app.pp.controller;

import app.pp.common.Result;
import app.pp.entity.Model;
import app.pp.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
