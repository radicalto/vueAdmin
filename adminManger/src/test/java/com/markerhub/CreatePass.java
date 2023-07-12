package com.markerhub;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import cn.hutool.core.map.MapUtil;
import com.markerhub.common.lang.Result;

public class CreatePass {
    public static void main(String[] args) {
        // 密码加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode("000000");
        System.out.println("password=====" + password);
//        密码验证
        boolean matches = bCryptPasswordEncoder.matches("000000", password);
    }
//    $10$kdF.JPOal9tfXKYnBgl51ufbUu2n536JDmRcw9GCxpMeDqgjz3c1y
//    $2a$10$fK5mAqDxWY0b5Od5CIQJK./z5Ergdc5gqQyPJmSU4pcdxOg.nWTsK
//    $2a$10$fmzMPe4Hrg0tIB/KMdVG4eWLv8vEzITt5XI2dNMc8egNYJW3TAhE2
}
