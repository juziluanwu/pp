package app.pp.service.impl;

import app.pp.entity.Group;
import app.pp.entity.SaleVoidRecord;
import app.pp.mapper.SaleVoidRecordMapper;
import app.pp.service.GroupService;
import app.pp.service.SaleVoidRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 作废销售单记录
 */
@Service
public class SaleVoidRecordServiceImpl implements SaleVoidRecordService {

    @Autowired
    private SaleVoidRecordMapper saleVoidRecordMapper;

    @Autowired
    private GroupService groupService;

    public List<SaleVoidRecord> selectall(Map<String, Object> param) {
        List<Group> groups = new ArrayList<>();
        if (param.get("groupid") != null) {
            Group g = new Group();
            g.setId((Integer) param.get("groupid"));
            groups.add(g);
        } else {
            groups = groupService.selectall();
        }
        param.put("groups", groups);
        return saleVoidRecordMapper.selectAll(param);
    }
}
