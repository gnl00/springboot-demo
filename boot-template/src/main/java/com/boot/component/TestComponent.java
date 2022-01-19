package com.boot.component;

import org.springframework.stereotype.Component;

/**
 * MyComponent
 *
 * @author lgn
 * @date 2021-11-16 10:18
 */

@Component
public class TestComponent {

    public String fun(Integer param) {

        int i = param;

        if (1 == i && param instanceof Integer ) {
            return "data: 11111";
        }

        return "data: default";
    }

}
