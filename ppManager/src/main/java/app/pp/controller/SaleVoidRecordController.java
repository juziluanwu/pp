package app.pp.controller;


import app.pp.common.AbstractController;
import app.pp.common.Result;
import app.pp.enums.ErrorEnum;
import app.pp.service.SaleVoidRecordService;
import app.pp.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/list")
    public Result list(@RequestParam(value = "devicenum", required = false) String devicenum,
                       @RequestParam(value = "pnum", required = false) String pnum,
                       @RequestParam(value = "salenum", required = false) String salenum,
                       @RequestParam(value = "creator", required = false) Integer creator,//操作人
                       @RequestParam(value = "groupid", required = false) Integer groupid,//所属车行
                       @RequestParam(value = "dstarttime", required = false) String dstarttime,
                       @RequestParam(value = "dendtime", required = false) String dendtime) {
        Map<String, Object> param = new HashMap<>();
        param.put("devicenum", devicenum);
        param.put("pnum", pnum);
        param.put("salenum", salenum);
        param.put("creator", creator);
        param.put("groupid", groupid);
        param.put("dstarttime", dstarttime);
        param.put("dendtime", dendtime);
        return ResultUtils.result(ErrorEnum.SUCCESS, saleVoidRecordService.selectall(param));
    }
}
