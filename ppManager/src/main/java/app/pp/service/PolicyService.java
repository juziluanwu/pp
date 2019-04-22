package app.pp.service;

import app.pp.common.Result;
import app.pp.entity.Policy;
import app.pp.entity.PolicyEntity;
import app.pp.entity.TransferCarEntity;

//保单号
public interface PolicyService {

    //生成保单号
    public Result verb(Policy policy);

    //删除保单号
    public Result del(Integer policyid);

    //保单号转移
    public Result transfer(TransferCarEntity transferCarEntity);

    //保单号查询
    public Result list(PolicyEntity policy);

}
