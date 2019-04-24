package app.pp.controller;


import app.pp.common.AbstractController;
import app.pp.common.Result;
import app.pp.enums.ErrorEnum;
import app.pp.service.PrintRecordService;
import app.pp.utils.ResultUtils;
import app.pp.vo.PrintVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/getPrintInfo/{id}")
    public Result getPrintInfo(@PathVariable(value = "id") Integer id) {
        return ResultUtils.result(ErrorEnum.SUCCESS, printRecordService.getPrintInfo(id));
    }

    /**
     * 打印
     */
    @PostMapping("/print/{id}")
    public Result print(@RequestBody PrintVO vo)  {
        printRecordService.print(vo);
        return ResultUtils.result(ErrorEnum.SUCCESS, "");
    }
}
