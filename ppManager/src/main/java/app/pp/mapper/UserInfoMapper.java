package app.pp.mapper;

import app.pp.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(UserInfo record);

    UserInfo selectByPrimaryKey(Integer uid);

    List<UserInfo> selectAll();

    int updateByPrimaryKey(UserInfo record);
}