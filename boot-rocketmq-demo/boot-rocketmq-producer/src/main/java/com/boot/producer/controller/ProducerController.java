package com.boot.producer.controller;

import com.boot.producer.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * ProducerController
 *
 * @author lgn
 * @since 2022/2/22 13:46
 */

@RestController
@RequestMapping("/prod")
public class ProducerController {

    private static volatile Map<String, Object> retMap = new HashMap<>();

    static {
        retMap.put("code", 200);
        retMap.put("message", "success");
        retMap.put("data", null);
    }

    @Autowired
    private ProducerService producer;

    @GetMapping("/test")
    public Map<String, Object> testConnect() {
        return retMap;
    }

    @GetMapping("/sendSync")
    public Map<String, Object> sendSync(@RequestParam(value = "msg") String msg) {
        producer.sendSync(msg);

        retMap.put("data", msg);
        return retMap;
    }

    @GetMapping("/sendAsync")
    public Map<String, Object> sendAsync(@RequestParam(value = "msg") String msg) {
        producer.sendAsync(msg);

        retMap.put("data", msg);
        return retMap;
    }

}
