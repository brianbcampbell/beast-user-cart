package beast.auth.jwt;

import beast.auth.model.UserDetails;
import com.google.common.collect.ImmutableMap;
import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class JwtUtils {

    @Value("${jwt.SecretKey}")
    private String jwtSecret;

    @Value("${jwt.ExpirationMs}")
    private int jwtExpirationMs;

    @Value("${jwt.CookieName}")
    private String jwtCookieName;

    private Key getKey() {
        return new SecretKeySpec(
                Base64.getDecoder().decode(jwtSecret),
                SignatureAlgorithm.HS512.getJcaName()
        );
    }

    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookieName);
        return Optional.ofNullable(cookie)
                .map(Cookie::getValue)
                .orElse(null);
    }

    public ResponseCookie generateJwtCookie(UserDetails userDetails) {
        String jwt = generateJwt(userDetails);
        return ResponseCookie
                .from(jwtCookieName, jwt)
                .path("/api")
                .maxAge(24 * 60 * 60)
                .httpOnly(true)
                .build();
    }

    private String generateJwt(UserDetails userDetails) {
        Map<String, Object> claims = ImmutableMap.of(
                //"authorities", userDetails.getAuthorities(),
                "email", userDetails.getEmail()
        );
        String jwt = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .addClaims(claims)  // "setClaims" will overwrite subject ^
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getKey(), SignatureAlgorithm.HS512)
                .compact();
        return jwt;
    }

    public ResponseCookie getCleanJwtCookie() {
        return ResponseCookie
                .from(jwtCookieName, null)
                .path("/api")
                .build();
    }

    public Claims parseClaimsFromJwt(String authToken) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(authToken)
                    .getBody();
            return claims;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        }

        return null;
    }

}
