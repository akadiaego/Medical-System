package com.ray.yygh.vo.hospital;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HospitalSetQueryVo {

    @ApiModelProperty(value = "医院名称")
    private String hospitalName;

    @ApiModelProperty(value = "医院编号")
    private String hospitalCode;
}
