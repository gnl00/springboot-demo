package com.boot.chat.bean;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;

/**
 * CacheDo
 *
 * @author lgn
 * @since 2022/3/24 16:21
 */

@Slf4j
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CacheDo {

    private String uid;

    private Session session;

}
