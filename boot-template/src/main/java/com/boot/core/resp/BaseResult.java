package com.boot.core.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * BaseResult
 *
 * @author lgn
 * @date 2021-11-15 17:36
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BaseResult<T> {

    private Integer code;
    private String msg;
    private T data;

}
