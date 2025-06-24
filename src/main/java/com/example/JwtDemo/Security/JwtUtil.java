package com.example.JwtDemo.Security;//package com.example.JwtDemo.Security;
//
//import com.example.JwtDemo.Models.Users;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwtParser;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.stereotype.Component;
//import java.security.Key;
//import java.util.Date;
//
//@Component
//public class JwtUtil {
//
//    private final String SECRET_KEY = "shabana_secret_2025_key_1234567890@2025";
//   // private  static long expiryDuration=60*60;
//    private final Key secretKey= Keys.secretKeyFor(SignatureAlgorithm.HS256);
//    public String generateToken(Users user1) {
//        long timeIn_ms=System.currentTimeMillis();
//      //  long expiryTime=timeIn_ms+expiryDuration*1000;
//        Date issuedDate = new Date(timeIn_ms);
//     //   Date expiryAt=new Date(expiryTime);
//        return Jwts.builder()
//                .setSubject(user1.getEmail())
//                .setIssuer("UserAuthApp")
//                .setIssuedAt(issuedDate)
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
//                .claim("id", user1.getId())
//                .claim("name", user1.getName())
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
//                .compact();
//    }
//
//
//    public Claims extractAllClaims(String token) {
//        JwtParser parser = Jwts
//                .parser()
//                .setSigningKey(secretKey) // `secretKey` should be a Key, not just bytes
//                .build();
//
//        return parser
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    public String extractEmail(String token) {
//        return extractAllClaims(token).getSubject();
//    }
//
//    public int extractUserId(String token) {
//        return (Integer) extractAllClaims(token).get("id");
//    }
//
//
//}
import com.example.JwtDemo.Models.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "shabana_secret_2025_key_1234567890@2025";
    private final Key secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public String generateToken(Users user1) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 1000 * 60 * 60); // 1 hour

        return Jwts.builder()
                .setSubject(user1.getEmail())
                .setIssuer("UserAuthApp")
                .setIssuedAt(now)
                .setExpiration(expiry)
                .claim("id", user1.getId())
                .claim("name", user1.getName())
                .signWith(secretKey)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

//    public String extractEmail(String token) {
//        return extractAllClaims(token).getSubject();
//    }

    public int extractUserId(String token) {
        return (Integer) extractAllClaims(token).get("id");
    }
}
