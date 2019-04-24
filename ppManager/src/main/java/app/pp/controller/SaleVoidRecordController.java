package app.pp.controller;


import app.pp.common.AbstractController;
import app.pp.common.Result;
import app.pp.entity.SaleVoidRecord;
import app.pp.enums.ErrorEnum;
import app.pp.service.SaleVoidRecordService;
import app.pp.utils.GlobleUtils;
import app.pp.utils.ResultUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 作废销售单记录
 */
@RestController
@RequestMapping("/salevoidrecord")
public class SaleVoidRecordController extends AbstractController {
    @Autowired
    private SaleVoidRecordService saleVoidRecordService;

    /**
     * 作废销售单列表
     */
    @GetMapping("/list/{page}")
    public Result list(@PathVariable(value = "page") Integer page,
                       @RequestParam(value = "devicenum", required = false) String devicenum,
                       @RequestParam(value = "pnum", required = false) String pnum,
                       @RequestParam(value = "salenum", required = false) String salenum,
                       @RequestParam(value = "creatorname", required = false) String creatorname,//操作人
                       @RequestParam(value = "shop4s", required = false) String shop4s,//所属车行
                       @RequestParam(value = "dstarttime", required = false) String dstarttime,
                       @RequestParam(value = "dendtime", required = false) String dendtime) {
        PageHelper.startPage(null == page ? 1 : page, GlobleUtils.DEFAULT_PAGE_SIZE);
        Map<String, Object> param = new HashMap<>();
        param.put("devicenum", devicenum);
        param.put("pnum", pnum);
        param.put("salenum", salenum);
        param.put("creatorname", creatorname);
        param.put("shop4s", shop4s);
        param.put("dstarttime", dstarttime);
        param.put("dendtime", dendtime);
        PageInfo<SaleVoidRecord> pageInfo = new PageInfo<>(saleVoidRecordService.selectall(param));
        return ResultUtils.result(ErrorEnum.SUCCESS, pageInfo);
    }
}
