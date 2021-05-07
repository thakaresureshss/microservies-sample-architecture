package com.lib.common.security.utils;

import com.lib.common.security.exceptions.InvalidJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtTokenProvider {

  private static final String ROLES = "roles";

  @Autowired
  JwtProperties jwtProperties;

  private String secretKey;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes());
  }

  public String generateAuthenticationToken(String username, List<String> roles) {
    Claims claims = Jwts.claims().setSubject(username);
    claims.put(ROLES, roles);
    Date now = new Date();
    Date validity = new Date(now.getTime() + jwtProperties.getValidityInMs());
    return Jwts.builder()//
        .setClaims(claims)//
        .setIssuedAt(now)//
        .setExpiration(validity)//
        .signWith(SignatureAlgorithm.HS256, secretKey)//
        .compact();
  }

  public Authentication getAuthentication(String token) {
    Claims claims = getAllClaimsFromToken(token);
    var roles = getRoleList(token);
    var authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

    User principal = new User(claims.getSubject(), "", authorities);
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        principal, token, authorities);
    authenticationToken = setAuthenticationDetailFrmHeader(token);
    // authenticationToken.setAuthenticated(true);
    authenticationToken.setDetails(claims);
    return authenticationToken;
  }

  private UsernamePasswordAuthenticationToken setAuthenticationDetailFrmHeader(String token) {
    UsernamePasswordAuthenticationToken authenticationToken;
    var roleList = getRoleList(token);
    String username = getUsername(token);
    var authorities = roleList.stream().map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
    User principal = new User((String) username, "", authorities);
    authenticationToken = new UsernamePasswordAuthenticationToken(principal, token, authorities);
    return authenticationToken;
  }

  public String getUsername(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7, bearerToken.length());
    }
    return null;
  }

  public boolean validateToken(String token) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

      if (claims.getBody().getExpiration().before(new Date())) {
        return false;
      }
      return true;
    } catch (SignatureException | MalformedJwtException e) {
      log.error("Unsupported JWT token trace: {}", e);
      throw new InvalidJwtException("invalid JWT token");
    } catch (ExpiredJwtException e) {
      log.error("Expired JWT token.");
      throw new InvalidJwtException("Expired JWT token");
    } catch (UnsupportedJwtException e) {
      log.error("Unsupported JWT token.");
      throw new InvalidJwtException("Unsupported JWT token");
    } catch (IllegalArgumentException e) {
      log.error("JWT token compact of handler are invalid.");
      throw new InvalidJwtException("JWT token compact of handler are invalid");
    } catch (JwtException e) {
      log.error("JWK exception.");
      throw new InvalidJwtException("Expired or invalid JWT token");
    }
  }

  public String getRefreshToken(String token) {
    validateToken(token);
    Claims claims = getAllClaimsFromToken(token);
    claims.setIssuedAt(new Date());
    claims.setExpiration(
        new Date(Instant.now().toEpochMilli() + jwtProperties.getRefreshValidityInMs()));
    String newToken = Jwts.builder().setClaims(claims)
        .signWith(SignatureAlgorithm.HS256, this.secretKey).compact();
    return newToken;
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    return claimsResolver.apply(getAllClaimsFromToken(token));
  }

  public Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
  }

  public long getExpirationDateFromToken(String token) {
    return (getClaimFromToken(token, Claims::getExpiration).getTime() - new Date().getTime())
        / 1000;
  }

  public List<String> getRoleList(String token) {
    return (List<String>) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody()
        .get(ROLES);
  }

  public String generateToken(String subject, Map<String, String> claims, Date validity) {
    // Generate a JWT Token
    JwtBuilder jwt = Jwts.builder().setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .signWith(SignatureAlgorithm.HS512, secretKey).setExpiration(validity);

    for (String claimsKey : claims.keySet()) {
      jwt.claim(claimsKey, claims.get(claimsKey));
    }
    return jwt.compact();
  }
}
