package app.pp.mapper;

import app.pp.entity.Tyre;
import app.pp.entity.TyreSsinfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface TyreSsinfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TyreSsinfo record);

    int insertSelective(TyreSsinfo record);

    TyreSsinfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TyreSsinfo record);

    int updateByPrimaryKey(TyreSsinfo record);

    int deteleBySsid(Integer ssid);

    TyreSsinfo selectBySsid(Integer ssid);

    String selectTyreById(Integer id);

    List<Tyre> alltyre();

}