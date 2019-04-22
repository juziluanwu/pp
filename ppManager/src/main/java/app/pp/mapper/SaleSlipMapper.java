package app.pp.mapper;

import app.pp.entity.SaleSlip;
import app.pp.entity.Saleman;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface SaleSlipMapper {
    int insert(SaleSlip slip);

    int update(SaleSlip slip);

    SaleSlip selectById(Integer id);

    List<SaleSlip> selectAll(Map<String, Object> param);
}
