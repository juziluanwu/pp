package app.pp.mapper;

import app.pp.entity.Model;
import app.pp.entity.SaleslipModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface SaleslipModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(SaleslipModel SaleslipModel);

    SaleslipModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SaleslipModel record);

    List<SaleslipModel> selectBySsid(Integer ssid);

    int deleteBySsid(Integer ssid);

    List<Model> selectModelBySsid(Integer ssid);
}