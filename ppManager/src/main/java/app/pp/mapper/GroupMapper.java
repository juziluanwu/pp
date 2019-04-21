package app.pp.mapper;

import app.pp.entity.Group;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface GroupMapper {
    int insert(Group group);
    int update (Group group);

    Group selectById(Integer id);

    /**
     * 查询子集
     * @param id
     * @return
     */
    List<Group> selectByPid(Integer id);

    /**
     * 查询子集的数量
     * @param id
     * @return
     */
    int selectCountByPid(Integer id);

    List<Group> selectAll();
}
