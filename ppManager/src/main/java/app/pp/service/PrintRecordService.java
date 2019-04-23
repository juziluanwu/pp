package app.pp.service;


import app.pp.entity.Group;
import app.pp.entity.SaleSlip;

import java.util.List;


/**
 * 打印
 */
public interface PrintRecordService {


    /**
     * 获取打印年限
     */
    int getPrintLimit(Integer id);

    SaleSlip getPrintInfo(Integer id);
}
