package app.pp.controller;


import app.pp.common.AbstractController;
import app.pp.common.Result;
import app.pp.entity.SaleSlip;
import app.pp.entity.Saleman;
import app.pp.enums.ErrorEnum;
import app.pp.service.DeviceService;
import app.pp.service.SaleSlipService;
import app.pp.service.SalemanService;
import app.pp.utils.ResultUtils;
import app.pp.vo.RenewalVO;
import app.pp.vo.SaleSlipDelVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 账号分组
 */
@RestController
@RequestMapping("/saleslip")
public class SaleSlipController extends AbstractController {
    @Autowired
    private SaleSlipService saleSlipService;
    @Autowired
    private DeviceService deviceService;
    /**
     * 销售单列表
     */
    @GetMapping("/list")
    public Result list(@RequestParam(value = "devicenum",required = false) String devicenum,
                       @RequestParam(value = "pnum",required = false) String pnum,
                       @RequestParam(value = "customername",required = false) String customername,
                       @RequestParam(value = "username",required = false) String username,
                       @RequestParam(value = "firstbeneficiary",required = false) Integer firstbeneficiary,
                       @RequestParam(value = "policystate",required = false) Integer policystate,
                       @RequestParam(value = "pstarttime",required = false) Date pstarttime,
                       @RequestParam(value = "pendtime",required = false) Date pendtime) {
        Map<String,Object> param = new HashMap<>();
        param.put("devicenum",devicenum);
        param.put("pnum",pnum);
        param.put("customername",customername);
        param.put("username",username);
        param.put("firstbeneficiary",firstbeneficiary);
        param.put("policystate",policystate);
        param.put("pstarttime",pstarttime);
        param.put("pendtime",pendtime);
        return ResultUtils.result(ErrorEnum.SUCCESS, saleSlipService.selectall(param));
    }




    /**
     * 新增销售单
     */
    @PostMapping("/save")
    @RequiresPermissions("sys:saleslip:save")
    public Result save(@RequestBody SaleSlip slip) {
        slip.setCreator(getUserId());
        slip.setCreatedtime(new Date());
        saleSlipService.save(slip);
        return ResultUtils.result(ErrorEnum.SUCCESS, "新增成功");
    }

    /**
     * 修改销售单
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:saleslip:update")
    public Result update(@RequestBody SaleSlip slip) {
        slip.setUpdator(getUserId());
        slip.setUpdatedtime(new Date());
        saleSlipService.update(slip);
        return ResultUtils.result(ErrorEnum.SUCCESS, "修改成功");
    }

    /**
     * 作废销售单
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:saleslip:delete")
    public Result delete(@RequestBody SaleSlipDelVO vo) {
        saleSlipService.delete(vo);
        return ResultUtils.result(ErrorEnum.SUCCESS, "作废成功");
    }

    /**
     * 销售单详情
     */
    @GetMapping("/info/{id}")
    public Result info(@PathVariable(value = "id")Integer id) {
        return ResultUtils.result(ErrorEnum.SUCCESS,  saleSlipService.info(id));
    }

    /**
     * 保单续期确认
     */
    @PostMapping("/renewal")
    @RequiresPermissions("sys:saleslip:renewal")
    public Result renewal(@RequestBody RenewalVO vo) {
        saleSlipService.renewal(vo);
        return ResultUtils.result(ErrorEnum.SUCCESS, "续期成功");
    }


    //验证设备号接口
    @PostMapping("/testDevice")
    public Result renewal(@RequestParam(value = "devicenum") String devicenum) {
        deviceService.testDevice(devicenum);
        return ResultUtils.result(ErrorEnum.SUCCESS, "设备号可以使用");
    }
}
