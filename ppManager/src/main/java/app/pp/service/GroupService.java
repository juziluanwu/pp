package app.pp.service;


import app.pp.entity.Group;

import java.util.List;


/**
 * 账号分组
 */
public interface GroupService {


    /**
     * 保存分组
     */
    void save(Group group);

    /**
     * 修改分组
     */
    void update(Group group);

    /**
     * 删除分组
     */
    void delete(Integer id);

    /**
     * 全部分组
     * @return
     */
    List<Group> selectall();
}
