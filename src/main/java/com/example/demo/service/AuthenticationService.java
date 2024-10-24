package com.example.demo.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.demo.domain.Token;
import com.example.demo.domain.User;
import com.example.demo.dto.request.AuthenticationRequest;
import com.example.demo.dto.request.IntrospectRequest;
import com.example.demo.dto.request.LogoutRequest;
import com.example.demo.dto.response.AuthenticationResponse;
import com.example.demo.dto.response.IntrospectResponse;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.TokenRepository;
import com.example.demo.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService {
    UserRepository userRepository;
    TokenRepository tokenRepository;


    // buidl dự án dùng mvn package -DskipTets
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.INVALID_KEY);

        }

        String token = generateToken(user);
        return AuthenticationResponse.builder()
                .Authenticated(true)
                .token(token)
                .build();

    }

    @NonFinal
   
    // @Value("${jwt.keys}")
    protected String signerKey = "3YjW35WxwwJHXS7NiQsNrdeilhj2wyqp5qcHmJlOeGLrVOoms6wcqvqP161tF2SC";
    

    private String generateToken(User user) {

        // tạo headers.
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        // TẠO BODY
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("ngocduong.com")
                .issueTime(new Date())
                // set tg cho token
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScop(user))

                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (Exception e) {
            log.warn("not created token");
            throw new RuntimeException(e);
        }
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException {

        var token = request.getToken();
        // JWSVerifier verifier = new MACVerifier(signerKey.getBytes());
        // SignedJWT signedJWT = SignedJWT.parse(token);
        // Date expityDate = signedJWT.getJWTClaimsSet().getExpirationTime();
        // var verified = signedJWT.verify(verifier);
        verifyToken(token);

        return IntrospectResponse.builder()
                .valid(true)
                .build();

    }

    // custom scop
    private String buildScop(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            // user.getRoles().forEach(s-> stringJoiner.add(s));
            // viet cách khác
            user.getRoles().forEach(role -> {
                // add role and permission vào trong scope
                stringJoiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions().forEach(permission -> stringJoiner.add("PERMISSION_" + permission.getName()));
                }
            });
        }
        return stringJoiner.toString();
    }

    public void logout(LogoutRequest token) throws JOSEException, ParseException {
        var signToken = verifyToken(token.getToken());

        String jit = signToken.getJWTClaimsSet().getJWTID();
        Date expiryDate = signToken.getJWTClaimsSet().getExpirationTime();
        Token mapperToken = Token.builder()
                .id(jit)
                .expiryTime(expiryDate)
                .build();

        tokenRepository.save(mapperToken);

    }
    
    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(signerKey.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expityDate = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
        if (verified && expityDate.after(new Date())) {
            throw new AppException(ErrorCode.INVALID_DOB);
        }
        return signedJWT;

    }
}
