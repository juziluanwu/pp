package app.pp.mapper;

import app.pp.entity.Car;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import javax.xml.ws.soap.MTOM;
import java.util.List;

@Component
@Mapper
public interface CarMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Car record);

    int insertSelective(Car record);

    Car selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Car record);

    int updateByPrimaryKey(Car record);

    List<Car> selectByFid(Integer fid);

    int updateById(Integer id);

    Car selectById(Integer id);
}