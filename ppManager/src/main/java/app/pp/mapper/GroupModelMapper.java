package app.pp.mapper;

import app.pp.entity.GroupModel;
import app.pp.entity.Model;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface GroupModelMapper {
    int insert(GroupModel groupModel);

    int deleteById(Integer id);

    int deleteByGid(Integer gid);

    List<GroupModel> selectByGid(Integer gid);

    List<Model> selectModelByGid(Integer gid);
}
