package com.markerhub.controller;

import cn.hutool.core.map.MapUtil;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.SysUser;
import com.markerhub.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class TextController extends BaseController{
    @Autowired
    SysUserService sysUserService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/text")
    public Object text(){
        List<SysUser> list = sysUserService.list();
        return  list;
    }

    @GetMapping("/redis")
    public void redisText() {
        redisUtil.set("textController","redis-18/11");
        System.out.println("ok==========ok");
    }

    @GetMapping("/text/pass")
    public Result passEncode() {
        // 密码加密
        String password = bCryptPasswordEncoder.encode("000000");
        System.out.println("password====="+password);
//        密码验证
        boolean matches = bCryptPasswordEncoder.matches("000000", password);
        return Result.succ(
                MapUtil.builder()
                        .put("password",password).put("matches",matches).build()
        );
    }

}
