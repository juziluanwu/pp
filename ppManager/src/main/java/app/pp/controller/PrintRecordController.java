package app.pp.controller;


import app.pp.common.AbstractController;
import app.pp.common.Result;
import app.pp.enums.ErrorEnum;
import app.pp.service.PrintRecordService;
import app.pp.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 打印
 */
@RestController
@RequestMapping("/print")
public class PrintRecordController extends AbstractController {
    @Autowired
    private PrintRecordService printRecordService;

    /**
     * 获取打印信息
     */
    @GetMapping("/print/{id}")
    public Result print(@PathVariable(value = "id") Integer id) {
        return ResultUtils.result(ErrorEnum.SUCCESS, printRecordService.getPrintInfo(id));
    }


}
