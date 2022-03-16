package com.boot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * Vehicle
 *
 * @author lgn
 * @since 2021/12/23 10:47
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Vehicle implements Serializable {

    private String cameraIndexCode;
    private String cameraAddress;
    private String cameraName;
    private String imageUrl;
    private String passId;
    private String passTime;
    private String plateNo;

}
