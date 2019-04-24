package app.pp.service;


import app.pp.entity.Group;
import app.pp.entity.SaleSlip;
import app.pp.vo.PrintVO;

import java.util.List;
import java.util.Map;


/**
 * 打印
 */
public interface PrintRecordService {


    Map<String,Object> getPrintInfo(Integer id);

    void print(PrintVO vo);
}
