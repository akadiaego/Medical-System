package com.ray.yygh.hospital.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ray.yygh.common.exception.YyghException;
import com.ray.yygh.common.utils.MD5;
import com.ray.yygh.hospital.service.HospitalSetService;
import com.ray.yygh.model.hospital.HospitalSet;
import com.ray.yygh.vo.hospital.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.ray.yygh.common.result.Result;

import java.util.List;
import java.util.Random;

@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hospital/hospitalSet")
public class HospitalSetController {

    //注入service
    @Autowired
    private HospitalSetService hospitalSetService;

    //查询医院表的表信息
    @ApiOperation(value = "获取所有医院")
    @GetMapping("findAll")
    public Result findAllHospitalSet() {
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }

    //逻辑删除
    @ApiOperation(value = "逻辑删除医院")
    @DeleteMapping("{id}")
    public Result removeHospitalSet(@PathVariable Long id) {
        boolean flag = hospitalSetService.removeById(id);
        if(flag) {
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    //条件查询带分页
    @PostMapping("findPage/{current}/{limit}")
    public Result findPageHospitalSet(@PathVariable long current,
                                      @PathVariable long limit,
                                      @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {
        //创建page对象，传递当前页，每页记录数
        Page<HospitalSet> page = new Page<>(current, limit);
        //构建条件
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        String hospitalName = hospitalSetQueryVo.getHospitalName();//医院名称
        String hospitalCode = hospitalSetQueryVo.getHospitalCode();//医院编号
        if (StringUtils.isEmpty(hospitalName)){
            wrapper.like("hospital_name", hospitalSetQueryVo.getHospitalName());
        }
        if (StringUtils.isEmpty(hospitalCode)){
            wrapper.eq("hospital_code",hospitalSetQueryVo.getHospitalCode());
        }

        //调用方法实现分页查询
        Page<HospitalSet> pageHospitalSet = hospitalSetService.page(page, wrapper);

        return Result.ok(pageHospitalSet);
    }

    //添加医院设置
    @PostMapping("saveHospitalSet")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet) {
        // 设置状态1 使用 0不能使用
        hospitalSet.setStatus(1);
        // 签名密钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+ random.nextInt(1000)));
        //调用Service

        boolean save = hospitalSetService.save(hospitalSet);
        if (save) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    // 根据id获取医院设置
    @GetMapping("getHospitalSet/{id}")
    public Result getHospitalSet(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    // 修改医院设置
    @PostMapping("updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet) {
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    // 批量删除医院设置
    @DeleteMapping("batchRemove")
    public Result batchRemoveHospitalSet(@RequestBody List<Long> idList) {
        hospitalSetService.removeByIds(idList);
        return Result.ok();
    }

    // 医院设置锁定和解锁
    @PutMapping("lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id,
                                  @PathVariable Integer status) {
        //根据id查询医院设置信息
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        // 设置状态
        hospitalSet.setStatus(status);
        // 调用方法
        hospitalSetService.updateById(hospitalSet);
        return Result.ok();
    }

    // 发送签名秘钥
    @PutMapping("sendKey/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hospitalCode = hospitalSet.getHospitalCode();
        // TODO 发送短信
        return Result.ok();
    }

}
