package com.example.SpringSecurityJWT.token;

import com.example.SpringSecurityJWT.security.CustomUserDetail;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import java.util.Date;
@Slf4j
public class JwtUtil {
    @Value("vietanh")
    private String JWT_SECRET;
    @Value("86400000")
    private int JWT_EXPIRATION;
    // Tạo jwwt từ thông tin User
    public String generateToken(CustomUserDetail customUserDetail){
        Date now = new Date();
        Date dateExprDate = new Date(now.getTime()+JWT_EXPIRATION);
        // Tao chuoi jwt tu userName
        return Jwts.builder()
                .setSubject(customUserDetail.getUsername())
                .setIssuedAt(now)
                .setExpiration(dateExprDate)
                .signWith(SignatureAlgorithm.HS256,JWT_SECRET)
                .compact();
    }
    // Lay thong tin user tu Jwt
    public String getUserNameFromJwt(String token){
        Claims claims = Jwts.parser().setSigningKey(JWT_SECRET)
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
    // Validate thong tin cua JWT
    public Boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException ex){
            log.error("token khong dung");
        }catch (ExpiredJwtException ex){
            log.error("token het han");
        }catch (UnsupportedJwtException ex){
            log.error("token khong ho tro");
        }catch (IllegalArgumentException ex){
            log.error("token khong co");
        }
        return false;
    }
}
