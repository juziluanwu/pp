package app.pp.controller;

import app.pp.common.Result;
import app.pp.entity.Policy;
import app.pp.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//保单号相关接口
@RestController
@RequestMapping("policy")
public class PolicyController {

    @Autowired
    PolicyService policyService;

    //批量生成保单号
    @PostMapping("verb")
    public Result verb(@RequestBody Policy policy){
        return policyService.verb(policy);
    }

    //删除保单号
    @GetMapping("del/{policyid}")
    public Result del(@PathVariable(value = "policyid") Integer policyid){

        return policyService.del(policyid);

    }
}
