package app.pp.controller;

import app.pp.common.Result;
import app.pp.enums.ErrorEnum;
import app.pp.service.DeviceService;
import app.pp.utils.ResultUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device")
public class DeviceController {
    @Autowired
    DeviceService deviceService;

    @GetMapping("list/{page}")
    @RequiresPermissions("sys:device:list")
    public Result list(@PathVariable(value = "page") Integer page){

        return deviceService.list(page);
    }

    @GetMapping("get")
    //@RequiresPermissions("sys:device:list")
    public Result get()throws Exception{
        deviceService.getDevice();
        return ResultUtils.result(ErrorEnum.SUCCESS,"00");

    }


}
