package app.pp.service;


import app.pp.entity.SaleVoidRecord;

import java.util.List;
import java.util.Map;


/**
 * 作废销售单记录
 */
public interface SaleVoidRecordService {


    /**
     * 作废销售单记录
     *
     * @return
     */
    List<SaleVoidRecord> selectall(Map<String, Object> param);
}
