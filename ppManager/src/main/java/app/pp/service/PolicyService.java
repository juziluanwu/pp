package app.pp.service;

import app.pp.common.Result;
import app.pp.entity.Policy;

//保单号
public interface PolicyService {

    //生成保单号
    public Result verb(Policy policy);

    //删除保单号
    public Result del(Integer policyid);

}
