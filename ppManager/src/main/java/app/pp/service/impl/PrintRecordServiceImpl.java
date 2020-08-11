package app.pp.service.impl;

import app.pp.entity.PrintRecord;
import app.pp.entity.SaleSlip;
import app.pp.entity.SysUserEntity;
import app.pp.entity.TyreSsinfo;
import app.pp.mapper.PrintRecordMapper;
import app.pp.mapper.SaleSlipMapper;
import app.pp.mapper.TyreSsinfoMapper;
import app.pp.service.ModelService;
import app.pp.service.PrintRecordService;
import app.pp.service.SaleSlipService;
import app.pp.vo.PrintVO;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class PrintRecordServiceImpl implements PrintRecordService {
    @Autowired
    private PrintRecordMapper printRecordMapper;
    @Autowired
    private SaleSlipService saleSlipService;
    @Autowired
    private SaleSlipMapper saleSlipMapper;
    @Autowired
    private ModelService modelService;
    @Autowired
    private TyreSsinfoMapper tyreSsinfoMapper;

    /**
     * 获取打印信息及年限
     *
     * @param id
     * @return
     */
    public Map<String, Object> getPrintInfo(Integer id) {
        Map<String, Object> result = new HashMap<>();
        SaleSlip ss = saleSlipService.info(id);
        if (ss != null) {
            TyreSsinfo tyreSsinfo = tyreSsinfoMapper.selectBySsid(id);
            if(tyreSsinfo != null){
                tyreSsinfo.setLfbrandname(tyreSsinfoMapper.selectTyreById(tyreSsinfo.getLfbrand()));
                tyreSsinfo.setLbbrandname(tyreSsinfoMapper.selectTyreById(tyreSsinfo.getLbbrand()));
                tyreSsinfo.setRfbrandname(tyreSsinfoMapper.selectTyreById(tyreSsinfo.getRfbrand()));
                tyreSsinfo.setRbbrandname(tyreSsinfoMapper.selectTyreById(tyreSsinfo.getRbbrand()));
                ss.setTyreSsinfo(tyreSsinfo);
            }
            result.put("saleslip", ss);
            //已打印年限
            int pl = printRecordMapper.selectSumdateBySaleslipid(id);
            if (pl == 0) {
                // 已打印年限为0 表示该销售单从未打印过   打印年限为销售单期限
                result.put("change", "change");
                result.put("printlimit", ss.getPolicydate());
            } else if (pl > 0) {
                //销售单已经打印过
                PrintRecord lastPR = printRecordMapper.selectLastBySaleslipid(ss.getId());
                //判断最近的打印记录的保险生效日期  == 销售单的保险生效日期 表示编辑过 打印期限不能选择 打印期限必须与以前相同
                if (lastPR.getPstarttime().compareTo(ss.getPstarttime()) == 0) {
                    result.put("change", "notchange");
                    result.put("printlimit", lastPR.getPrintlimit());
                } else if (lastPR.getPstarttime().compareTo(ss.getPstarttime()) < 0) {
                    //判断最近的打印记录的保险生效日期  <= 销售单的保险生效日期 表示续期过  打印期限可以选择  为 销售单总期限 - 以前打印期限总和
                    result.put("change", "change");
                    result.put("printlimit", ss.getPolicydate() - pl);
                }
            }

            result.put("model", modelService.getCurrentGroupModel());
        }
        return result;
    }

    /**
     * 打印
     *
     * @param vo
     */
    @Transactional
    public void print(PrintVO vo) {
        SaleSlip ss = saleSlipService.info(vo.getSaleslipid());
        if (ss != null) {

            SaleSlip newss = new SaleSlip();
            newss.setId(ss.getId());
            //将销售单变更为 打印状态
            newss.setPrintstate(2);
            //将销售单修改为 不能修改状态
            newss.setDelstate(3);
            saleSlipMapper.update(newss);

            PrintRecord lastRecord = printRecordMapper.selectLastBySaleslipid(vo.getSaleslipid());
            if (lastRecord == null || (lastRecord != null && ss.getPstarttime().compareTo(lastRecord.getPstarttime()) > 0)) {
                //如果lastRecord == null 表示该销售单没有打印记录  肯定为 未打印状态  需要记录 打印年限

                //如果 lastRecord != null 表示该销售单已经打印过  销售单的生效日期比打印记录的生效日期晚 表明已经续期  需要再次记录打印期限
                PrintRecord pr = new PrintRecord();
                pr.setSaleslipid(vo.getSaleslipid());
                pr.setPrintlimit(vo.getDate());
                pr.setPstarttime(ss.getPstarttime());
                pr.setCreatedtime(new Date());
                SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
                pr.setCreator(user.getUserId());
                printRecordMapper.insert(pr);
            }
        }
    }
}
