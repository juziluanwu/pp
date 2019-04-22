package app.pp.controller;

import app.pp.common.Result;
import app.pp.entity.Car;
import app.pp.service.CarService;
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
    public Result saveCar(@RequestBody Car car){

        return carService.save(car);
    }

    //删除品牌
    @GetMapping("del/{id}")
    public Result del(@PathVariable(value = "id") Integer id){
        return  carService.del(id);
    }
    //删除车系
    @PostMapping("dels")
    public Result dels(@RequestBody List<Integer>ids){

        return  carService.dels(ids);
    }


    //编辑品牌或车系
    @PostMapping("update")
    public Result update(@RequestBody Car car){
            return  carService.update(car);
    }

    //品牌或车系详情
    @GetMapping("info/{id}")
    public Result info(@PathVariable(value = "id") Integer id){
            return  carService.info(id);
    }

    //品牌车系列表
    @GetMapping("list/{fid}/{page}")
    public Result list(@PathVariable(value = "fid") Integer fid,@PathVariable(value = "page") Integer page){
            return  carService.list(fid,page);
    }
}
