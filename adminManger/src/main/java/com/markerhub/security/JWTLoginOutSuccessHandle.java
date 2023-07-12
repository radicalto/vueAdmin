package com.markerhub.security;

import cn.hutool.json.JSONUtil;
import com.markerhub.common.lang.Result;
import com.markerhub.utils.JwtUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
@Component
public class JWTLoginOutSuccessHandle implements LogoutSuccessHandler, LogoutHandler {
    @Autowired
    JwtUtils jwtUtils;
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

    }

    @SneakyThrows
    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {

            if (authentication != null){ //authentication 身份信息
                // 手动退出 SecurityContextLogoutHandler内置退出处理器
                new SecurityContextLogoutHandler().logout(httpServletRequest,httpServletResponse,authentication);
            }

            httpServletResponse.setContentType("application/json;charset=UTF-8");
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            // 清除jwt
            httpServletResponse.setHeader(jwtUtils.getHeader(),"");

            Result result = Result.succ("");
            outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));

            outputStream.flush();
            outputStream.close();
    }
}
