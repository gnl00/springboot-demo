package com.boot.chat.bean;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * OnlineDo
 *
 * @author lgn
 * @since 2022/3/25 14:59
 */

@Slf4j
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OnlineDo {

    private String uid;

    private String sessionId;

}
