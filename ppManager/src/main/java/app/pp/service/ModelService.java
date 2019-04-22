package app.pp.service;

/*
*
* 保单模版
*
*/

import app.pp.common.Result;
import app.pp.entity.Model;

public interface ModelService {

    //新增保单模版
    public Result saveModel(Model model);

}
