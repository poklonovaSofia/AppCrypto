package org.example.spring.services.user.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.spring.models.auth.User;
import org.example.spring.repositories.auth.UserRepository;
import org.example.spring.requests.UserLoginRequest;
import org.example.spring.responses.UserLoginResponse;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public boolean isTokenExpired(Claims claims) {
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }
    @Autowired
    public JwtUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public UserLoginResponse signIn(UserLoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("User с указанным email не найден"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Password");
        }

        String accessToken = generateAccessToken(user.getId(), user.getEmail());

        return new UserLoginResponse(user.getId(), user.getEmail(), accessToken);
    }

    @Value("${jwt.secret}")
    private String secretKey;
    private String generateAccessToken(Long userId, String email) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(key)
                .compact();
    }

    public UserLoginResponse signUp(User user) {
        String accessToken = generateAccessToken(user.getId(), user.getEmail());
        return new UserLoginResponse(user.getId(), user.getEmail(), accessToken);
    }

    public User getUserData(String token) throws Exception {
        boolean flag = true;
        Claims claims = parseToken(token);
        if (claims != null) {
            if (!isTokenExpired(claims)) {
                String userIdString = claims.getSubject();
                Long userId = Long.parseLong(userIdString);
                Optional<User> userOptional = userRepository.findById(userId);
                if (userOptional.isPresent()) {
                    return userOptional.get();
                }

            }
            else flag=false;

        }
        else flag=false;
        if(!flag)
            throw new Exception("token");
        return null;

    }

    public Claims parseToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
            return Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean checkValidToken(String token) {
        Claims claims = parseToken(token);
        if (claims != null) {
            if (!isTokenExpired(claims)) {
                return true;
            }
        }
        return false;
    }
}
