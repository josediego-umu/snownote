package com.um.snownote.jwtUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.um.snownote.controller.UserController;
import com.um.snownote.dto.UserDTO;
import com.um.snownote.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.hibernate.validator.internal.util.privilegedactions.NewInstance;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.PublicKey;
import java.time.Instant;
import java.util.*;

public class JwtUtil {

    private static final String CIPHER = "AES";
    private static final SecretKey SECRETY_KEY = getSigningKey();
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    public static String generateToken(long expirationMillis, UserDTO user) throws Exception {

        try {
            Date now = new Date();
            Date expirateDate = new Date(now.getTime() + expirationMillis);

            return Jwts.builder()
                    .id(UUID.randomUUID().toString())
                    .header().type("JWT")
                    .and()
                    .issuer("SnowNote")
                    .subject(user.getId())
                    .claim("user", user)
                    .expiration(expirateDate)
                    .issuedAt(Date.from(Instant.now()))
                    .signWith(SECRETY_KEY)
                    .compact();


        } catch (Exception e) {
            logger.error("Error in generateToken", e);
            throw new Exception("Error in generateToken");
        }


    }

    public static Claims validateToken(String token) {
        try {
            token = token.replace("Bearer ", "");
            return Jwts.parser().verifyWith(SECRETY_KEY).build().parseSignedClaims(token).getPayload();

        } catch (JwtException | IllegalArgumentException e) {

            throw new JwtException("JWT token is invalid or expired");
        }
    }

    public static User getUserFromToken(String token) {

        Claims claims = validateToken(token);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(claims.get("user"), User.class);
    }

    private static SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(getRandomKey(CIPHER, 256).getEncoded());
    }

    private static SecretKey getRandomKey(String cipher, int keySize) {
        byte[] randomKeyBytes = new byte[keySize / 8];
        Random random = new Random();
        random.nextBytes(randomKeyBytes);
        return new SecretKeySpec(randomKeyBytes, cipher);
    }

}
