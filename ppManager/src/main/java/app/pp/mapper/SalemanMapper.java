package app.pp.mapper;

import app.pp.entity.Saleman;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface SalemanMapper {
    int insert(Saleman saleman);

    int update(Saleman saleman);

    Saleman selectById(Integer id);

    List<Saleman> selectAll(Map<String,Object> param);
}
