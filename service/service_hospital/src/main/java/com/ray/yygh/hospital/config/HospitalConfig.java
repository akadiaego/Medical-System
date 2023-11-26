package com.ray.yygh.hospital.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.ray.yygh.hospital.mapper")
public class HospitalConfig {
}
