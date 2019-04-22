package app.pp.service.impl;

import app.pp.common.Result;
import app.pp.entity.Model;
import app.pp.entity.SysRoleMenuEntity;
import app.pp.enums.ErrorEnum;
import app.pp.mapper.ModelMapper;
import app.pp.service.ModelService;
import app.pp.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/*
    保单模版的实现类
 */
@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    ModelMapper modelMapper;

    @Override
    //添加保单模版的实现
    public Result saveModel(Model model) {
        SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
        model.setCreatetime(new Date());
        model.setCreateuser(sysRoleMenuEntity.getId());
        modelMapper.insertSelective(model);
        return ResultUtils.result(ErrorEnum.SUCCESS,"保存成功");
    }
}
