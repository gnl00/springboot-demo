package com.demo.entity;

import lombok.*;

import java.io.Serializable;

/**
 * TODO
 *
 * @author gnl
 * @since 2023/3/15
 */
@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = -7101345780866828082L;
    private Integer id;
    private String name;
    private Integer age;
}
