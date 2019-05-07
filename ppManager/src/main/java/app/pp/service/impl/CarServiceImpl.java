package app.pp.service.impl;

import app.pp.common.Result;
import app.pp.entity.Car;
import app.pp.entity.PolicyEntity;
import app.pp.entity.SysUserEntity;
import app.pp.enums.ErrorEnum;
import app.pp.mapper.CarMapper;
import app.pp.service.CarService;
import app.pp.utils.GlobleUtils;
import app.pp.utils.ResultUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    CarMapper carMapper;

    @Override
    //品牌车系添加
    public Result save(Car car) {
        SysUserEntity s = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        car.setCreatedtime(new Date());
        car.setCreator(s.getUserId());
        int i = carMapper.insertSelective(car);
        if(i>0){
            return ResultUtils.result(ErrorEnum.SUCCESS,"添加成功");
        }else{
            return ResultUtils.fail("添加失败");
        }

    }

    @Override
    //删除车行或车系
    public Result del(Integer id) {
        //先判断是品牌行还是车系

        int  num = carMapper.updateById(id);

        if(num==0){
            return  ResultUtils.fail("删除失败");
        }
        return ResultUtils.result(ErrorEnum.SUCCESS,"删除成功");
    }

    @Override
    //删除车系
    public Result dels(List<Integer> ids) {
        int num = 0;
        for(int i = 0;i<ids.size();i++){
            num = carMapper.updateById(ids.get(i));
        }
        if(num == 0)
            return ResultUtils.fail("删除失败");
        return ResultUtils.result(ErrorEnum.SUCCESS,"删除成功");
    }

    @Override
    //更新品牌或车系
    public Result update(Car car) {
        SysUserEntity s = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        car.setCreator(s.getUserId());
        car.setCreatedtime(new Date());
        int i = carMapper.updateByPrimaryKeySelective(car);

        if(i == 0)
        return  ResultUtils.fail("更新失败");

        return ResultUtils.result(ErrorEnum.SUCCESS,"更新成功");
    }

    @Override
    //车系或品牌详情
    public Result info(Integer id) {
        return ResultUtils.result(ErrorEnum.SUCCESS,carMapper.selectById(id));
    }

    @Override
    //品牌车系列表
    public Result list(Integer fid,Integer page) {
        PageHelper.startPage(null == page ? 1 : page, GlobleUtils.DEFAULT_PAGE_SIZE);
        PageInfo<Car> pageInfo = new PageInfo<Car>(carMapper.selectByFid(fid));
        return ResultUtils.result(ErrorEnum.SUCCESS,pageInfo);
    }
}
