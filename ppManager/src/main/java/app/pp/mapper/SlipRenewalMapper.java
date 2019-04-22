package app.pp.mapper;

import app.pp.entity.SlipRenewal;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SlipRenewalMapper {
    int insert(SlipRenewal slipRenewal);
}
