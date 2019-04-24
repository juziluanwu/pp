package app.pp.mapper;

import app.pp.entity.PrintRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface PrintRecordMapper {
    int insert(PrintRecord printRecord);
    int deleteBySaleslipid(Integer saleslipid);
    //销售单打印期限总和
    int selectSumdateBySaleslipid(Integer saleslipid);
    //查询最近一次打印的期限
    int selectLastLimitBySaleslipid(Integer saleslipid);
}
