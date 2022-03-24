package neowise.user.service.security.authentication;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;

import org.apache.commons.lang3.time.DateUtils;

import neowise.user.service.common.JsonUtil;
import neowise.user.service.config.AwsConfig;
import neowise.user.service.config.Env;
import neowise.user.service.security.JwtMetadata;
import neowise.user.service.security.common.AuthAttributes;
import neowise.user.service.security.common.AuthRole;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

public class AuthenticationToken {

    private static final String GLOBAL_AWS_SECRET_JWT = Env.get("GLOBAL_JWT_AWS_SECRET_NAME");
    private static final String GLOBAL_JWT_SECRET_ID = "GLOBAL_JWT_SECRET";
    private static final String GLOBAL_JWT_ISSUER_ID = "GLOBAL_JWT_ISSUER_ID";
    private final static int TOKEN_EXPIRY_DAYS = 15;

    private static JwtMetadata getMetaData() {
        SecretsManagerClient secretsClient = SecretsManagerClient.builder().region(AwsConfig.AWS_DEFAULT_REGION)
                .credentialsProvider(AwsConfig.AWS_CREDENTIALS_PROVIDER).build();
        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder().secretId(GLOBAL_AWS_SECRET_JWT)
                .build();
        GetSecretValueResponse getSecretValueResponse = secretsClient.getSecretValue(getSecretValueRequest);
        String secret = getSecretValueResponse.secretString();
        JsonNode secretJson = JsonUtil.parseJson(secret, JsonNode.class);
        String jwtSecret = secretJson.get(GLOBAL_JWT_SECRET_ID).asText();
        String jwtIssuer = secretJson.get(GLOBAL_JWT_ISSUER_ID).asText();
        JwtMetadata jwtMetadata = new JwtMetadata();
        jwtMetadata.setIssuer(jwtIssuer);
        jwtMetadata.setSecret(jwtSecret);
        return jwtMetadata;
    }

    public static String encode(String userId) {
        return encodeWithRole(AuthAttributes.USER_ID, userId, AuthRole.USER);
    }

    public static String encodeGuestToken(String guestId) {
        return encodeWithRole(AuthAttributes.GUEST_ID, guestId, AuthRole.GUEST_USER);
    }

    public static String encodeWithRole(String claimName, String claimValue, AuthRole role) {
        JwtMetadata jwtMetadata = getMetaData();
        Date expiresAt = DateUtils.addDays(new Date(), TOKEN_EXPIRY_DAYS);
        String token = JWT.create().withExpiresAt(expiresAt).withClaim(claimName, claimValue)
                .withClaim(AuthAttributes.ROLE, role.toString()).withIssuer(jwtMetadata.getIssuer())
                .sign(Algorithm.HMAC256(jwtMetadata.getSecret()));
        return token;
    }

    public static DecodedJWT verifyAndDecode(String token) {
        JwtMetadata jwtMetadata = getMetaData();
        Algorithm algorithm = Algorithm.HMAC256(jwtMetadata.getSecret());
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(jwtMetadata.getIssuer()).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt;
    }

    public static String getUserId(String globalToken) {
        DecodedJWT decodedGlobalToken = verifyAndDecode(globalToken);
        String userId = decodedGlobalToken.getClaim(AuthAttributes.USER_ID).as(TextNode.class).asText();
        return userId;
    }
}
