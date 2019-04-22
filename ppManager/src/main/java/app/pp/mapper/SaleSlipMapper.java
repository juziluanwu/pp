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

    /**
     * 关联其他表获取的详细数据
     * @param id
     * @return
     */
    SaleSlip selectById(Integer id);

    /**
     * 只获取当前表的对应数据
     * @param id
     * @return
     */
    SaleSlip findById(Integer id);

    List<SaleSlip> selectAll(Map<String, Object> param);
}
