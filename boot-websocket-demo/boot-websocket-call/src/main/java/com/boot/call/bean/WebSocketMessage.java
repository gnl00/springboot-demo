package com.boot.call.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * WebSocketMessage
 *
 * @author lgn
 * @since 2022/4/15 11:04
 */

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class WebSocketMessage {

    private String src;

    private String target;

    private String type;

    /**
     * @JsonIgnore 进行 Json String to Object 反序列化的时候忽略字段 sdp 和 candidate，只需要关注 src 和 target 用来进行消息转发
     */
    @JsonIgnore
    private String sdp;
    @JsonIgnore
    private String candidate;

}
