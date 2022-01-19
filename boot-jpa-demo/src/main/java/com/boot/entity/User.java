package com.boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User
 *
 * @author lgn
 * @since 2022/1/5 15:46
 */

@Table(name = "tb_user")
@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "") // 主键生成策略
    private Long id;

    private String name;

    private Integer age;

}
