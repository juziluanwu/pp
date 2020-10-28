package app.pp.controller;


import app.pp.common.AbstractController;
import app.pp.common.Result;
import app.pp.entity.Model;
import app.pp.entity.SaleSlip;
import app.pp.enums.ErrorEnum;
import app.pp.exceptions.GlobleException;
import app.pp.service.DeviceService;
import app.pp.service.SaleSlipService;
import app.pp.utils.ResultUtils;
import app.pp.vo.RenewalVO;
import app.pp.vo.SaleSlipDelVO;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 销售单
 */
@RestController
@RequestMapping("/saleslip")
public class SaleSlipController extends AbstractController {
    @Autowired
    private SaleSlipService saleSlipService;
    @Autowired
    private DeviceService deviceService;

    /**
     * 销售单列表
     */
    @GetMapping("/list/{page}")
    public Result list(@PathVariable(value = "page") Integer page,
                       @RequestParam(value = "devicenum", required = false) String devicenum,
                       @RequestParam(value = "pnum", required = false) String pnum,
                       @RequestParam(value = "customername", required = false) String customername,
                       @RequestParam(value = "username", required = false) String username,
                       @RequestParam(value = "firstbeneficiary", required = false) String firstbeneficiary,
                       @RequestParam(value = "printstate", required = false) Integer printstate,
                       @RequestParam(value = "pstarttime", required = false) String pstarttime,
                       @RequestParam(value = "pendtime", required = false) String pendtime,
                       @RequestParam(value = "chname", required = false) String chname,
                       @RequestParam(value = "brandid", required = false) Integer brandid) {

        Map<String, Object> param = new HashMap<>();
        param.put("devicenum", devicenum);
        param.put("pnum", pnum);
        param.put("customername", customername);
        param.put("username", username);
        param.put("firstbeneficiary", firstbeneficiary);
        param.put("printstate", printstate);
        param.put("pstarttime", pstarttime);
        param.put("pendtime", pendtime);
        param.put("chname", chname);
        param.put("brandid", brandid);
        param.put("page", page);
        PageInfo<SaleSlip> pageInfo = new PageInfo<>(saleSlipService.selectallpage(param));
        return ResultUtils.result(ErrorEnum.SUCCESS, pageInfo);
    }


    /**
     * 导出
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/exportExcel")
    @RequiresPermissions("sys:saleslip:export")
    public void exportExcel(HttpServletResponse response,
                            @RequestParam(value = "devicenum", required = false) String devicenum,
                            @RequestParam(value = "pnum", required = false) String pnum,
                            @RequestParam(value = "customername", required = false) String customername,
                            @RequestParam(value = "username", required = false) String username,
                            @RequestParam(value = "firstbeneficiary", required = false) String firstbeneficiary,
                            @RequestParam(value = "printstate", required = false) Integer printstate,
                            @RequestParam(value = "pstarttime", required = false) String pstarttime,
                            @RequestParam(value = "pendtime", required = false) String pendtime,
                            @RequestParam(value = "chname", required = false) String chname
    ) throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("devicenum", devicenum);
        param.put("pnum", pnum);
        param.put("customername", customername);
        param.put("username", username);
        param.put("firstbeneficiary", firstbeneficiary);
        param.put("printstate", printstate);
        param.put("pstarttime", pstarttime);
        param.put("pendtime", pendtime);
        param.put("chname", chname);
        List<SaleSlip> list = saleSlipService.selectall(param);
        if (list != null && !list.isEmpty()) {
            String fileName = "销售单.xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("销售单");
            String[] headers = {"销售单号", "设备号", "保单号", "销售单状态",
                    "保单金额", "4S店名", "车主姓名", "手机号", "赔付限额",
                    "车牌号码", "保险开始日期", "保险结束日期", "第一受益人", "车架号码", "模板"};
            HSSFRow row = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            int rowNum = 1;
            for (SaleSlip vo : list) {
                HSSFRow row3 = sheet.createRow(rowNum);
                row3.createCell(0).setCellValue(vo.getSalenum());
                row3.createCell(1).setCellValue(vo.getDevicenum());
                row3.createCell(2).setCellValue(vo.getPnum());
                if (1 == vo.getPrintstate()) {
                    row3.createCell(3).setCellValue("未打印");
                } else if (2 == vo.getPrintstate()) {
                    row3.createCell(3).setCellValue("已打印");
                }
                if (vo.getPolicyamount() != null) {
                    row3.createCell(4).setCellValue(vo.getPolicyamount().toString());
                }
                row3.createCell(5).setCellValue(vo.getShop4s());
                row3.createCell(6).setCellValue(vo.getCustomername());
                row3.createCell(7).setCellValue(vo.getPhone());
                if (vo.getCompensation() != null) {
                    row3.createCell(8).setCellValue(vo.getCompensation().toString());
                }
                row3.createCell(9).setCellValue(vo.getCarnum());
                row3.createCell(10).setCellValue(sdf.format(vo.getPstarttime()));
                row3.createCell(11).setCellValue(sdf.format(vo.getPendtime()));
                row3.createCell(12).setCellValue(vo.getFirstbeneficiaryname());
                row3.createCell(13).setCellValue(vo.getFrame());
                List<Model> models = saleSlipService.selectModelBySsid(vo.getId());
                if (models != null && models.size() > 0) {
                    String name = "";
                    for (Model m : models) {
                        name = name + m.getName()+",";
                    }
                    row3.createCell(14).setCellValue(name);
                }
                rowNum++;
            }

            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            OutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } else {
            throw new GlobleException("没有查询数据");
        }
    }


    /**
     * 新增销售单
     */
    @PostMapping("/save")
    @RequiresPermissions("sys:saleslip:save")
    public Result save(@RequestBody SaleSlip slip) {
        slip.setCreator(getUserId());
        slip.setCreatedtime(new Date());
        saleSlipService.save(slip);
        return ResultUtils.result(ErrorEnum.SUCCESS, "新增成功");
    }

    /**
     * 修改销售单
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:saleslip:update")
    public Result update(@RequestBody SaleSlip slip) {
        slip.setUpdator(getUserId());
        slip.setUpdatedtime(new Date());
        saleSlipService.update(slip);
        return ResultUtils.result(ErrorEnum.SUCCESS, "修改成功");
    }

    /**
     * 作废销售单
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:saleslip:delete")
    public Result delete(@RequestBody SaleSlipDelVO vo) {
        saleSlipService.delete(vo);
        return ResultUtils.result(ErrorEnum.SUCCESS, "作废成功");
    }

    /**
     * 销售单详情
     */
    @GetMapping("/info/{id}")
    public Result info(@PathVariable(value = "id") Integer id) {
        return ResultUtils.result(ErrorEnum.SUCCESS, saleSlipService.info(id));
    }

    /**
     * 保单续期确认
     */
    @PostMapping("/renewal")
    public Result renewal(@RequestBody RenewalVO vo) {
        saleSlipService.renewal(vo);
        return ResultUtils.result(ErrorEnum.SUCCESS, "续期成功");
    }

    /**
     * 点击保单续期获取续期相关信息
     */
    @GetMapping("/getRenewalInfo/{id}")
    @RequiresPermissions("sys:saleslip:renewal")
    public Result getRenewalInfo(@PathVariable(value = "id") Integer id) {
        return ResultUtils.result(ErrorEnum.SUCCESS, saleSlipService.getRenewalInfo(id));
    }


    //验证设备号接口
    @GetMapping("/testDevice")
    public Result renewal(@RequestParam(value = "devicenum") String devicenum) {
        return ResultUtils.result(ErrorEnum.SUCCESS, deviceService.testDevice(devicenum));
    }

    @GetMapping("/tyre")
    public Result tyre() {
        return ResultUtils.result(ErrorEnum.SUCCESS, saleSlipService.tyre());
    }

}
