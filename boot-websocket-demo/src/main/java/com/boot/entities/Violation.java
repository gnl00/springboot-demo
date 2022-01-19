package com.boot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * Violation
 *
 * @author lgn
 * @since 2021/12/23 10:47
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Violation implements Serializable {

    private String cameraAddress;
    private String cameraName;
    private Integer crossingId;
    private String cameraIndexCode;
    private String dataSource;
    private String directionIndex;
    private String imageUrl;
    private String imageUrl2;
    private String laneName;
    private String laneNo;
    private String passId;
    private String passTime;
    private String pdvsPosition;
    private Integer picUrlNum;
    private String pilotPosition;
    private String plateColor;
    private String plateImageUrl;
    private String plateNo;
    private String platePosition;
    private String plateType;
    private String tfsIndex;
    private String vehicleColor;
    private String vehicleLogo;
    private String vehicleSpeed;
    private String vehicleType;
    private String vicepilotPosition;
    private String violationId;
    private String violationPlaceIndex;
    private String violationType;

}
