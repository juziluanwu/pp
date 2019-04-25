package app.pp.controller;

import app.pp.common.Result;
import app.pp.entity.Policy;
import app.pp.entity.PolicyEntity;
import app.pp.entity.TransferCarEntity;
import app.pp.enums.ErrorEnum;
import app.pp.service.GroupService;
import app.pp.service.PolicyService;
import app.pp.utils.ResultUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//保单号相关接口
@RestController
@RequestMapping("policy")
public class PolicyController {

    @Autowired
    PolicyService policyService;
    @Autowired
    GroupService groupService;

    //批量生成保单号
    @PostMapping("verb")
    @RequiresPermissions("sys:policy:save")
    public Result verb(@RequestBody Policy policy){
        return policyService.verb(policy);
    }

    //删除保单号
    @GetMapping("del/{policyid}")
    @RequiresPermissions("sys:policy:delete")
    public Result del(@PathVariable(value = "policyid") Integer policyid){

        return policyService.del(policyid);

    }

    //保单号转移车行
    @PostMapping("transfer")
    @RequiresPermissions("sys:policy:transfer")
    public Result transfer(@RequestBody TransferCarEntity transferCarEntity){
        return policyService.transfer(transferCarEntity);
    }
    //保单号查询
    @PostMapping("list")
    @RequiresPermissions("sys:policy:list")
    public Result list(@RequestBody PolicyEntity policy){
        return policyService.list(policy);
    }

    //查询当前分组下的所有车行
    @GetMapping("group")
    public Result group(){
        return ResultUtils.result(ErrorEnum.SUCCESS,groupService.selecGroup());
    }

}
