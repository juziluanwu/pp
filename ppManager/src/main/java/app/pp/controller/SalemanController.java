package app.pp.controller;


import app.pp.common.AbstractController;
import app.pp.common.Result;
import app.pp.entity.Saleman;
import app.pp.enums.ErrorEnum;
import app.pp.service.SalemanService;
import app.pp.utils.GlobleUtils;
import app.pp.utils.ResultUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 销售人员
 */
@RestController
@RequestMapping("/saleman")
public class SalemanController extends AbstractController {
    @Autowired
    private SalemanService salemanService;

    /**
     * 销售人员列表
     */
    @GetMapping("/list/{page}")
    public Result list(@PathVariable(value = "page") Integer page,
                       @RequestParam(value = "name", required = false) String name,
                       @RequestParam(value = "phone", required = false) String phone) {
        PageHelper.startPage(null == page ? 1 : page, GlobleUtils.DEFAULT_PAGE_SIZE);
        Map<String, Object> param = new HashMap<>();
        param.put("name", name);
        param.put("phone", phone);
        PageInfo<Saleman> pageInfo = new PageInfo<>(salemanService.selectall(param));
        return ResultUtils.result(ErrorEnum.SUCCESS, pageInfo);
    }


    /**
     * 新增销售人员
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
     * 修改销售人员
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
     * 删除销售人员
     */
    @GetMapping("/delete/{id}")
    @RequiresPermissions("sys:saleman:delete")
    public Result delete(@PathVariable(value = "id") Integer id) {
        salemanService.delete(id);
        return ResultUtils.result(ErrorEnum.SUCCESS, "删除成功");
    }


    /**
     * 下拉框
     */
    @GetMapping("/selectlist")
    public Result selectlist() {
        Map<String, Object> param = new HashMap<>();
        param.put("groupid", getUser().getGroupid());
        return ResultUtils.result(ErrorEnum.SUCCESS, salemanService.selectall(param));
    }
}
