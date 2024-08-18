package com.roadguardianbackend.roadguardianbackend.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.roadguardianbackend.roadguardianbackend.domain.UserPrincipal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Component
public class JWTTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    public String generateJwtToken(UserPrincipal userPrincipal) {
        String[] claims = getClaimsFromUser(userPrincipal);
        return JWT.create()
                .withIssuer("YourCompanyHere")
                .withAudience("AdminApplication")
                .withIssuedAt(new Date())
                .withSubject(userPrincipal.getUsername())
                .withArrayClaim("authorities", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000)) // Expiry time is 24 hours
                .sign(Algorithm.HMAC512(secret.getBytes(StandardCharsets.UTF_8)));
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        try {
            String[] claims = getClaimsFromToken(token);
            return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        } catch (JWTDecodeException e) {
            // Log the exception or handle accordingly
            return new ArrayList<>();
        }
    }

    public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, null, authorities);
        authenticationToken.setDetails(new ServletWebRequest((jakarta.servlet.http.HttpServletRequest) request));
        return authenticationToken;
    }

    public boolean isTokenValid(String username, String token) {
        if (StringUtils.isBlank(token)) {
            return false;
        }

        try {
            JWTVerifier verifier = getJWTVerifier();
            DecodedJWT jwt = verifier.verify(token);
            return StringUtils.isNotEmpty(username) && !isTokenExpired(jwt);
        } catch (Exception e) {
            // Log or handle the exception if needed
            return false;
        }
    }

    public String getSubject(String token) {
        try {
            JWTVerifier verifier = getJWTVerifier();
            DecodedJWT verifiedJwt = verifier.verify(token);
            return verifiedJwt.getSubject();
        } catch (JWTDecodeException e) {
            // Log or handle the decode exception
            return null;
        }
    }

    private boolean isTokenExpired(DecodedJWT jwt) {
        Date expiration = jwt.getExpiresAt();
        return expiration.before(new Date());
    }

    private String[] getClaimsFromToken(String token) throws JWTDecodeException {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getClaim("authorities").asArray(String.class);
    }

    private JWTVerifier getJWTVerifier() {
        Algorithm algorithm = Algorithm.HMAC512(secret.getBytes(StandardCharsets.UTF_8));
        return JWT.require(algorithm)
                .withIssuer("YourCompanyHere")
                .build();
    }

    private String[] getClaimsFromUser(UserPrincipal user) {
        List<String> authorities = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
            authorities.add(grantedAuthority.getAuthority());
        }
        return authorities.toArray(new String[0]);
    }
}