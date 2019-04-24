package app.pp.service.impl;

import app.pp.common.Result;
import app.pp.entity.Model;
import app.pp.entity.SysUserEntity;
import app.pp.enums.ErrorEnum;
import app.pp.mapper.GroupModelMapper;
import app.pp.mapper.ModelMapper;
import app.pp.service.ModelService;
import app.pp.service.SysUserService;
import app.pp.utils.GlobleUtils;
import app.pp.utils.ResultUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/*
    保单模版的实现类
 */
@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private GroupModelMapper groupModelMapper;

    @Override
    //添加保单模版的实现
    public Result saveModel(Model model) {
        SysUserEntity s = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        model.setCreatetime(new Date());
        model.setCreateuser(s.getUserId());
        int i = modelMapper.insertSelective(model);
        if (i > 0) {
            return ResultUtils.result(ErrorEnum.SUCCESS, "保存成功");
        } else {
            return ResultUtils.fail("保存失败");
        }

    }

    @Override
    //删除保单模板
    public Result del(Integer id) {
        Model model = new Model();
        model.setId(id);
        model.setIsdel(1);
        int i = modelMapper.updateByPrimaryKeySelective(model);
        if (i > 0) {
            return ResultUtils.result(ErrorEnum.SUCCESS, "模板删除成功");
        } else {
            return ResultUtils.fail("模板删除失败");
        }

    }

    @Override
    //启用禁用模板
    public Result disoren(Integer id, Integer state) {
        Model model = new Model();
        model.setId(id);
        model.setState(state);
        int i = modelMapper.updateByPrimaryKeySelective(model);
        if (i > 0) {
            return ResultUtils.result(ErrorEnum.SUCCESS, "操作成功");
        } else {
            return ResultUtils.fail("操作失败");
        }
    }

    @Override
    //模板列表查询
    public Result list(Integer page) {
        PageHelper.startPage(null == page ? 1 : page, GlobleUtils.DEFAULT_PAGE_SIZE);
        PageInfo<Model> pageInfo = new PageInfo<Model>(modelMapper.selectAll());
        return ResultUtils.result(ErrorEnum.SUCCESS, pageInfo);
    }

    public List<Model> getCurrentGroupModel() {
        SysUserEntity user = sysUserService.getCurrentUser();
        return groupModelMapper.selectModelByGid(user.getGroupid());
    }
}
