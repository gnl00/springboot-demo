package com.boot.chat.bean;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * WSGroup
 *
 * @author lgn
 * @since 2022/4/1 10:07
 */

@Slf4j
@Data
@Accessors(chain = true)
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WSGroup {

    private String gid;

    private List<String> members;

}
