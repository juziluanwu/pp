package app.pp.mapper;

import app.pp.entity.Log;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface LogMapper {
    int insert(Log record);
}