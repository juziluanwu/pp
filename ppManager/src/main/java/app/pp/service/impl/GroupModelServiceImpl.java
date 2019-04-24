package app.pp.service.impl;

import app.pp.entity.GroupModel;
import app.pp.mapper.GroupModelMapper;
import app.pp.service.GroupModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 分组打印模板
 */
@Service
public class GroupModelServiceImpl implements GroupModelService {

    @Autowired
    private GroupModelMapper groupModelMapper;


    public void save(GroupModel groupModel) {
        groupModelMapper.insert(groupModel);
    }


}
