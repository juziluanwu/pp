package app.pp.mapper;

import app.pp.entity.Model;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Model record);

    int insertSelective(Model record);

    Model selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Model record);

    int updateByPrimaryKey(Model record);

    List<Model> selectAll();
}