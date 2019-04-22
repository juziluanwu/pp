package app.pp.controller;


import app.pp.common.AbstractController;
import app.pp.common.Result;
import app.pp.entity.Group;
import app.pp.entity.Saleman;
import app.pp.enums.ErrorEnum;
import app.pp.service.GroupService;
import app.pp.service.SalemanService;
import app.pp.utils.ResultUtils;
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
@RequestMapping("/saleman")
public class SalemanController extends AbstractController {
    @Autowired
    private SalemanService salemanService;

    /**
     * 分组菜单
     */
    @GetMapping("/list")
    public Result list(@RequestParam(value = "name",required = false) String name,
                       @RequestParam(value = "name",required = false) String phone) {
        Map<String,Object> param = new HashMap<>();
        param.put("name",name);
        param.put("phone",phone);
        return ResultUtils.result(ErrorEnum.SUCCESS, salemanService.selectall(param));
    }




    /**
     * 新增分组
     */
    @PostMapping("/save")
    @RequiresPermissions("sys:saleman:save")
    public Result save(@RequestBody Saleman saleman) {
        saleman.setCreator(getUserId());
        saleman.setCreatedtime(new Date());
        salemanService.save(saleman);
        return ResultUtils.result(ErrorEnum.SUCCESS, "新增成功");
    }

    /**
     * 修改分组
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:saleman:update")
    public Result update(@RequestBody Saleman saleman) {
        saleman.setUpdator(getUserId());
        saleman.setUpdatedtime(new Date());
        salemanService.update(saleman);
        return ResultUtils.result(ErrorEnum.SUCCESS, "修改成功");
    }

    /**
     * 删除分组
     */
    @GetMapping("/delete/{id}")
    @RequiresPermissions("sys:saleman:delete")
    public Result delete(@PathVariable(value = "id")Integer id) {
        salemanService.delete(id);
        return ResultUtils.result(ErrorEnum.SUCCESS, "删除成功");
    }


    /**
     * 下拉框
     */
    @GetMapping("/selectlist")
    public Result selectlist() {
        Map<String,Object> param = new HashMap<>();
        param.put("creator",getUserId());
        return ResultUtils.result(ErrorEnum.SUCCESS, salemanService.selectall(param));
    }
}
