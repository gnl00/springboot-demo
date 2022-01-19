package com.boot.controller;

import org.springframework.web.bind.annotation.*;

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
