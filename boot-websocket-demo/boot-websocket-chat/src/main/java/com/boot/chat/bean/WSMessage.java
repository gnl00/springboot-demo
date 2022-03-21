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

    /**
     * 消息体类型 text or img or video
     */
    private String bodyType;

    /**
     * 消息类型：分为 1、initial 初始化消息 2、update 更新消息 3、contact 对话消息
     */
    private String msgType;

    private Boolean read = false;

}
