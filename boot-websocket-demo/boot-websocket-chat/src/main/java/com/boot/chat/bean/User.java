package com.boot.chat.bean;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author lgn
 * @since 2022/3/22 16:58
 */

@Slf4j
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String account;

    private String password;

}
