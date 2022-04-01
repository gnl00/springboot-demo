package com.boot.chat.bean;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * WSMessage websocket message 实体类
 *
 * @author lgn
 * @since 2022/3/17 9:56
 */

@Slf4j
@Data
@Accessors(chain = true)
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WSMessage<T> {

    private String from;

    private String to;

    private long date = System.currentTimeMillis();

    private T body;

    private String type;

    private Boolean read = false;

    private WSGroup group;

}
