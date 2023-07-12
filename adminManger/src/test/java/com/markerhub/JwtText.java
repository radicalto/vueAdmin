package com.markerhub;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtText {
    String username = "sys_Tom";
    Long expire = 1000L;
    String secret = "ji8n3439n439n43ld9ne9343fdfer49h";
    public static void main(String[] args) {
        JwtText jwtText = new JwtText();
        String token = jwtText.getToken();
        Claims chaim = jwtText.getChaimByToken(token);
        System.out.println("chaim======"+chaim);
        System.out.println("Subject======"+chaim.getSubject());
        System.out.println("Expiration======"+chaim.getExpiration().before(new Date()));
    }

    public String getToken(){
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime()+1000 * expire);
        JwtBuilder jwtBuilder = Jwts.builder().setHeaderParam("typ", "JWT")
                .setSubject(username).setIssuedAt(nowDate)// 设置签发时间
                .setExpiration(expireDate).signWith(SignatureAlgorithm.HS512, secret);// 用于设置签名秘钥

        return jwtBuilder.compact();
    }

    public Claims getChaimByToken(String jwt){
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).getBody();
        return claims;
    }
}
