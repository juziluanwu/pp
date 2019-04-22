package app.pp.mapper;

import app.pp.entity.PrintRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface PrintRecordMapper {
    int insert(PrintRecord printRecord);
}
