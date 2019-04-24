package app.pp.service;


import app.pp.entity.Group;
import app.pp.entity.GroupModel;

import java.util.List;


/**
 * 账号分组
 */
public interface GroupModelService {


    /**
     * 保存分组
     */
    void save(GroupModel groupModel);

}
