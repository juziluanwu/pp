package app.pp.service;

import app.pp.common.Result;
import app.pp.entity.Car;

import java.util.List;

//品牌车系相关接口
public interface CarService {

    //添加品牌或车系
    public Result save(Car car);

    //删除品牌或车系
    public Result del(Integer id);

    //删除车系
    public Result dels(List<Integer> ids);

    //更新品牌或车系
    public Result update(Car car);

    //查询牌品或车系详情
    public Result info(Integer id);

    //品牌车系列表、
    public Result list(Integer fid,Integer page);
    //品牌下拉框
    List<Car> select1();
    //车系拉框
    List<Car> selectByFid(Integer Fid);

    //品牌车系列表不分页、
    public Result listtwo(Integer fid);
}
