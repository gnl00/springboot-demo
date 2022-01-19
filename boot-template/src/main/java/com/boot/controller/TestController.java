package com.boot.controller;

import com.boot.component.TestComponent;
import com.boot.core.resp.BaseResult;
import com.boot.core.util.CustomAsserts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController
 *
 * @author lgn
 * @date 2021-11-15 16:20
 */

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestComponent testComponent;

    @GetMapping(value = "/func/{param}")
    public BaseResult func(@PathVariable Integer param) {
        return new BaseResult(200, "success", testComponent.fun(param));
    }


    @GetMapping(value = "/funcTrue")
    public BaseResult funcTrue() {

        String ret = "this is the msg for True return.";

        log.info("im here funcTure");

        CustomAsserts.assertTrue(true, "this is true msg.");


        return new BaseResult(200, "success", ret);
    }

    @GetMapping("/funcFalse")
    public BaseResult<String> funcFalse() {

        String ret = "this is the msg for False return.";

        log.info("im here funcFalse");

        CustomAsserts.assertTrue(false, "this is false msg, when error comes out.");

        return new BaseResult(200, "success", ret);
    }

}
