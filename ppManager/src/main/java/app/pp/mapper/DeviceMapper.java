package app.pp.mapper;

import app.pp.entity.Device;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper

public interface DeviceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(List<Device> record);

    int insertSelective(Device record);

    Device selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Device record);

    int updateByPrimaryKey(Device record);

    List<Device> selectAll();

    int updateSynState(@Param(value = "id") Integer id,@Param(value = "synstate") Integer synstate);

    List<Device> list();
}