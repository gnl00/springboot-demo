package com.boot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * WebsocketController
 *
 * @author lgn
 * @since 2021/12/22 18:57
 */

@RestController
@RequestMapping("/test")
public class WebsocketController {

    public static int n = 0;

    @ResponseBody
    @GetMapping("/clear")
    public String clear() {

        n = 0;

        return "clear success";
    }

}
