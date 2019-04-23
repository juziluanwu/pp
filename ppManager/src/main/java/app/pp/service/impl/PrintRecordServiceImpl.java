package app.pp.service.impl;

import app.pp.entity.SaleSlip;
import app.pp.mapper.PrintRecordMapper;
import app.pp.service.PrintRecordService;
import app.pp.service.SaleSlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrintRecordServiceImpl implements PrintRecordService {
    @Autowired
    private PrintRecordMapper printRecordMapper;
    @Autowired
    private SaleSlipService saleSlipService;


    public int getPrintLimit(Integer id){
        return printRecordMapper.selectSumdateBySaleslipid(id);
    }

    public SaleSlip getPrintInfo(Integer id){
        SaleSlip ss = saleSlipService.info(id);
        if(ss != null){
            ss.setPrintlimit(getPrintLimit(id));
        }
        return  ss;
    }
}
