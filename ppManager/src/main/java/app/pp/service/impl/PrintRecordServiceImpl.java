package app.pp.service.impl;

import app.pp.entity.PrintRecord;
import app.pp.entity.SaleSlip;
import app.pp.mapper.PrintRecordMapper;
import app.pp.service.PrintRecordService;
import app.pp.service.SaleSlipService;
import app.pp.vo.PrintVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrintRecordServiceImpl implements PrintRecordService {
    @Autowired
    private PrintRecordMapper printRecordMapper;
    @Autowired
    private SaleSlipService saleSlipService;

    public SaleSlip getPrintInfo(Integer id){
        SaleSlip ss = saleSlipService.info(id);
        if(ss != null){
            //已打印年限
            int pl = printRecordMapper.selectSumdateBySaleslipid(id);
            if(pl == 0){
                // 已打印年限为0 表示该销售单从未打印过   打印年限为销售单期限
                ss.setPrintlimit(pl);
            }else if(pl >0){
                //销售单已经打印过

                if(1 == ss.getPrintstate()){
                    //销售单现在为未打印状态 表明 销售单续期 后 打印
                    ss.setPrintlimit(ss.getPolicydate() - pl);
                }else if(2 == ss.getPrintstate()){
                    //销售单现在为已打印状态  表明 现在是重复打印  期限与上次期限相同
                    ss.setPrintlimit(printRecordMapper.selectLastLimitBySaleslipid(id));
                }
            }


        }
        return  ss;
    }

    public void print(PrintVO vo){
        //打印  如果是未打印状态  则要记录  打印记录
        SaleSlip  ss = saleSlipService.info(vo.getSaleslipid());
        if(ss != null){
            if(1 == ss.getPrintstate()){
                PrintRecord pr = new PrintRecord();
                pr.setSaleslipid(vo.getSaleslipid());
                pr.setPrintlimit(vo.getDate());
                printRecordMapper.insert(pr);
            }
        }
    }
}
