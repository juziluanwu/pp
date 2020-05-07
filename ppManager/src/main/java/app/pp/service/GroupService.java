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

    /**
     * 获取当前账号的分组
     * @return
     */
    Group getCurrentGroup();

    Group info(Integer id);

    List<Group> firstbeneficiarylist();

     List<Group> selecGroup();

    List<Group> getAllCarGroup();
    List<Group> chehang();

    List<Group> selectAll();
}
