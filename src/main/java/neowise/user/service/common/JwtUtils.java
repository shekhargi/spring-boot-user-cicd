package neowise.user.service.common;

import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtils {

    public static String extractBearerToken(String authorizationHeader) {
        String token = null;
        String[] headerParts = authorizationHeader.split(" ");
        String tokenType = headerParts[0];

        if (tokenType.equals("Bearer")) {
            token = headerParts[1];
        }

        return token;
    }

    public static DecodedJWT decodeToken(String jwtSecret, String jwtIssuer, String token) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(jwtIssuer).build(); // Reusable verifier instance
        DecodedJWT jwt = verifier.verify(token);
        return jwt;
    }

    public static String encodeToken(String jwtSecret, String jwtIssuer, Map<String, ?> payloadClaims) {
        Algorithm algorithm = Algorithm.HMAC256(jwtIssuer);
        return JwtUtils.encodeToken(jwtSecret, jwtIssuer, algorithm, payloadClaims);
    }

    public static String encodeToken(String jwtSecret, String jwtIssuer, Algorithm algorithm,
            Map<String, ?> payloadClaims) {

        String token = JWT.create().withPayload(payloadClaims).withIssuer(jwtIssuer).sign(algorithm);
        return token;
    }
}
