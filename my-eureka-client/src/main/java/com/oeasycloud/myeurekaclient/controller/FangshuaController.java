package com.oeasycloud.myeurekaclient.controller;

import com.oeasycloud.myeurekaclient.annotation.AccessLimit;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/4/9
 */
@RestController
public class FangshuaController {

    @AccessLimit(seconds = 5, maxCount = 5, needLogin = true)
    @RequestMapping("/fangshua")
    public String fangshua() {
        return "SUCCESS!";
    }
}
