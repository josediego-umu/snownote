package com.um.snownote.jwtUtils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class JwtTokenAspect {
    @Around("@annotation(JwtTokenRequired)")
    public Object validateToken(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();

        String token = (String) args[0];

        try {
            Claims claims = JwtUtil.validateToken(token);

            return joinPoint.proceed();

        } catch (JwtException e) {
            throw new JwtException(e.getMessage());
        }

    }
}
