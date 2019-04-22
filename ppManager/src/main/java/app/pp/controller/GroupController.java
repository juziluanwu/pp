package app.pp.controller;


import app.pp.common.AbstractController;
import app.pp.common.Result;
import app.pp.entity.Group;
import app.pp.entity.SysUserEntity;
import app.pp.enums.ErrorEnum;
import app.pp.service.GroupService;
import app.pp.utils.Assert;
import app.pp.utils.GlobleUtils;
import app.pp.utils.ResultUtils;
import app.pp.vo.PasswordForm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 账号分组
 */
@RestController
@RequestMapping("/group")
public class GroupController extends AbstractController {
    @Autowired
    private GroupService groupService;

    /**
     * 分组菜单
     */
    @GetMapping("/list")
    public Result list() {
        List<Group> list = groupService.selectall();
        return ResultUtils.result(ErrorEnum.SUCCESS, list);
    }




    /**
     * 新增分组
     */
    @PostMapping("/save")
    @RequiresPermissions("sys:group:save")
    public Result save(@RequestBody Group group) {
        group.setCreator(getUserId());
        group.setCreatedtime(new Date());
        groupService.save(group);
        return ResultUtils.result(ErrorEnum.SUCCESS, "新增成功");
    }

    /**
     * 修改分组
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:group:update")
    public Result update(@RequestBody Group group) {
        group.setUpdator(getUserId());
        group.setUpdatedtime(new Date());
        groupService.update(group);
        return ResultUtils.result(ErrorEnum.SUCCESS, "修改成功");
    }

    /**
     * 删除分组
     */
    @GetMapping("/delete/{id}")
    @RequiresPermissions("sys:group:delete")
    public Result delete(@PathVariable(value = "id")Integer id) {
        groupService.delete(id);
        return ResultUtils.result(ErrorEnum.SUCCESS, "删除成功");
    }
}
