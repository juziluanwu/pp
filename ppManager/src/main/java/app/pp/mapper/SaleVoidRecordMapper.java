package app.pp.mapper;

import app.pp.entity.SaleVoidRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface SaleVoidRecordMapper {
    int insert(SaleVoidRecord slip);

    int update(SaleVoidRecord slip);

   // SaleVoidRecord selectById(Integer id);

    List<SaleVoidRecord> selectAll(Map<String, Object> param);
}
