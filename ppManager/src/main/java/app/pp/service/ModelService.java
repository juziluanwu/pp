package app.pp.service;

/*
*
* 保单模版
*
*/

import app.pp.common.Result;
import app.pp.entity.Model;

import java.util.List;

public interface ModelService {

    //新增保单模版
    public Result saveModel(Model model);

    //删除保单模板
    public Result del(Integer id);

    //禁用启用模板
    public Result disoren(Integer id,Integer state);

    //查询模板列表
    public Result list(Integer page);

    //获取当前用户分组的打印模板
    List<Model> getCurrentGroupModel();
}
