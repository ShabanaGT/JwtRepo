package com.example.JwtDemo.Security;//package com.example.JwtDemo.Security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.security.Key;
//@Component
//public class JwtFilter  extends OncePerRequestFilter {
//
//    private final String SECRET_KEY = "shabana_secret_2025_key_1234567890@2025";
//
//    private final Key secretKey= Keys.secretKeyFor(SignatureAlgorithm.HS256);
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String path = request.getRequestURI();
//
//        if (path.contains("/login") || path.contains("/createUser")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String authHeader = request.getHeader("Authorization");
//        System.out.println("Authorization Header: " + authHeader);
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            sendUnauthorized(response, " Token is missing");
//            return;
//        }
//
//        String token = authHeader.substring(7); // Remove "Bearer "
//        try {
//            Claims claims = Jwts.parser()
//                    .setSigningKey(secretKey)
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody();
//
//            System.out.println(" Token valid");
//
//        } catch (Exception ex) {
//            sendUnauthorized(response, "Invalid or expired token");
//            return;
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private void sendUnauthorized(HttpServletResponse response, String message) throws IOException {
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.getWriter().write(message);
//    }
//
//}

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final String SECRET_KEY = "shabana_secret_2025_key_1234567890@2025";
    private final Key secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.contains("/User")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendUnauthorized(response, "Token is missing");
            return;
        }

        String token = authHeader.substring(7);
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            System.out.println("Token valid");

        } catch (Exception ex) {
            sendUnauthorized(response, "Invalid or expired token");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void sendUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(message);
    }


}
