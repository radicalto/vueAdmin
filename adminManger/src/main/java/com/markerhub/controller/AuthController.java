package com.markerhub.controller;

import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.markerhub.common.lang.Const;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.SysUser;
import com.markerhub.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;
@Slf4j
@RestController
public class AuthController extends BaseController {
    @Autowired
    Producer producer;
    @Autowired
    SysUserService sysUserService;
/**    * 图片验证码    */
    @GetMapping("/captcha")
    public Result captcha() throws IOException {
        // 存储在localStorage 中，每次调用接口的时候都把它当成一个字段传给后台
        String key = UUID.randomUUID().toString();
        // 验证码
        String code = producer.createText();
        System.out.println("code======="+code);
        System.out.println("key======="+key);
        // 生成验证码图片
        BufferedImage image = producer.createImage(code);//BufferedImage 将图片读入内存
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);// 保存图片
        BASE64Encoder encoder = new BASE64Encoder();
        String str = "data:image/jpeg;base64,"; // 图片头
        String base64Img = str + encoder.encode(outputStream.toByteArray());
        // 存储到redis中
        redisUtil.hset(Const.CAPTCHA_KEY,key,code,60);
        log.info("验证码 -- {} - {}",key,code);
        System.out.println("生成验证码成功==========");
        return Result.succ(MapUtil.builder()
                .put("token",key).put("captchaImg",base64Img)
                .build());
    }

    @GetMapping("/sys/userInfo")
    public Result userInfo(Principal principal) {
        String username = principal.getName();
        SysUser sysUser = sysUserService.getByUsername(username);
        return Result.succ(MapUtil.builder()
                .put("id",sysUser.getId())
                .put("username",sysUser.getUsername())
                .put("avatar",sysUser.getAvatar())
                .map()
        );
    }
}
