package com.markerhub.utils;

import io.jsonwebtoken.*;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
@Data
@ConfigurationProperties(prefix = "markerhub.jwt")
public class JwtUtils {
    private Long expire;
    private String secret;
    private String header;
    // 生成jwt
    public String generateToken(String username) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime()+1000 * expire);

        return Jwts.builder().setHeaderParam("typ","JWT")// jwt的header头信息
                .setSubject(username)// 有效载荷(Payload)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)// 7天过期
                .signWith(SignatureAlgorithm.HS512, secret)// Signature加密的签名
                .compact();
    }

    // 解析jwt
    public Claims getClaimByToken(String jwt) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    // jwt是否过期
    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
}
