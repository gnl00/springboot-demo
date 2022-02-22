package com.boot.producer.controller;

import com.boot.producer.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProducerController
 *
 * @author lgn
 * @since 2022/2/22 13:46
 */

@RestController
@RequestMapping("/prod")
public class ProducerController {

    @Autowired
    private ProducerService producerService;

    @GetMapping("/send")
    public String send(@RequestParam(value = "msg", required = true) String msg) {
        producerService.send(msg);
        return "send success: " + msg;
    }

}
