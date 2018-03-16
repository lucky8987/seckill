package org.seckill.web;

import java.util.Date;
import java.util.List;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/seckill")
public class SeckillController {

    private static final Logger log = LoggerFactory.getLogger(SeckillController.class);

    @Autowired
    private SeckillService service;

    @GetMapping("/list")
    public String list(Model model) {
        List<Seckill> seckillList = service.getSeckillList();
        model.addAttribute("list", seckillList);
        return "list";
    }

    @GetMapping("/{seckillId}/detail")
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        if (seckillId == null) {
            return "redirect:/seckill/list";
        }
        Seckill seckill = service.getSeckillById(seckillId);
        if (seckill == null) {
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill", seckill);
        return "detail";
    }

    @PostMapping(value = "/{seckillId}/exposer", produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public SeckillResult exposer(@PathVariable("seckillId") Long seckillId) {
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = service.exportSeckillUrl(seckillId);
            result = new SeckillResult<>(true, exposer);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new SeckillResult<>(false,e.getMessage());
        }
        return result;
    }

    @PostMapping(value = "/{seckillId}/{md5}/execution", produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public SeckillResult execute(@PathVariable("seckillId") Long seckillId, @PathVariable("md5") String md5, @CookieValue(value = "killPhone", required = false) Long phone) {
        if (phone == null) {
            return new SeckillResult(false,SeckillStateEnum.NOT_LOGIN);
        }
        try {
            SeckillExecution seckillExecution = service.executeSeckill(seckillId, md5, phone);
            return new SeckillResult(true, seckillExecution);
        } catch (SeckillException e) {
            return new SeckillResult(true, new SeckillExecution(seckillId, SeckillStateEnum.END));
        } catch (RepeatKillException e) {
            return new SeckillResult(true, new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new SeckillResult(true, new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR));
        }
    }

    @GetMapping(value = "/time/now", produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Long> time() {
        Date now = new Date();
        return new SeckillResult<Long>(true, now.getTime());
    }
}
