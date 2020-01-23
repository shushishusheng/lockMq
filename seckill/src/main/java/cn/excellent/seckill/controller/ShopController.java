package cn.excellent.seckill.controller;

import cn.excellent.seckill.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ShopController {

    @Autowired
    private ShopService shopService;

    @RequestMapping("/plan")
    @ResponseBody
    public String plan() {
        shopService.plan();
        return "准备完成";
    }

    @RequestMapping("/start")
    @ResponseBody
    public String startShop() {

        String msg = "";
        msg = shopService.startShop();
        return msg;
    }

}
