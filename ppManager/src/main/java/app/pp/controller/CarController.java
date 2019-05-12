package app.pp.controller;

import app.pp.common.Result;
import app.pp.entity.Car;
import app.pp.enums.ErrorEnum;
import app.pp.service.CarService;
import app.pp.utils.ResultUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//车辆品牌相关接口
@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    CarService carService;
    //车辆品牌添加
    @PostMapping("save")
    @RequiresPermissions("sys:car:save")
    public Result saveCar(@RequestBody Car car){

        return carService.save(car);
    }

    //删除品牌
    @GetMapping("del/{id}")
    @RequiresPermissions("sys:car:delete")
    public Result del(@PathVariable(value = "id") Integer id){
        return  carService.del(id);
    }
    //删除车系
    @PostMapping("dels")
    @RequiresPermissions("sys:car:deletes")
    public Result dels(@RequestBody List<Integer>ids){

        return  carService.dels(ids);
    }


    //编辑品牌或车系
    @PostMapping("update")
    @RequiresPermissions("sys:car:update")
    public Result update(@RequestBody Car car){
            return  carService.update(car);
    }

    //品牌或车系详情
    @GetMapping("info/{id}")
    @RequiresPermissions("sys:car:info")
    public Result info(@PathVariable(value = "id") Integer id){
            return  carService.info(id);
    }

    //品牌车系列表
    @GetMapping("list/{fid}/{page}")
    @RequiresPermissions("sys:car:list")
    public Result list(@PathVariable(value = "fid") Integer fid,@PathVariable(value = "page") Integer page){
            return  carService.list(fid,page);
    }

    @GetMapping("select1")
    public Result select1(){

        return ResultUtils.result(ErrorEnum.SUCCESS, carService.select1());
    }

    @GetMapping("select2/{fid}")
    public Result selectByFid(@PathVariable(value = "fid") Integer fid){
        return ResultUtils.result(ErrorEnum.SUCCESS, carService.selectByFid(fid));
    }

    //品牌车系列表
    @GetMapping("listtwo/{fid}")
    public Result listtwo(@PathVariable(value = "fid") Integer fid){
        return  carService.listtwo(fid);
    }
}
