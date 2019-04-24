package app.pp.service;


import app.pp.entity.Group;
import app.pp.entity.SaleSlip;
import app.pp.vo.PrintVO;

import java.util.List;


/**
 * 打印
 */
public interface PrintRecordService {


    SaleSlip getPrintInfo(Integer id);

    void print(PrintVO vo);
}
