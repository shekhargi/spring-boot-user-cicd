package neowise.user.service.security.authentication;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import neowise.user.service.exceptions.AuthenticationException;
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException;

@Service
public class AuthenticationService {

    private String hashClientId(String clientId) {
        String hashedClientId = DigestUtils.sha256Hex(clientId);
        return hashedClientId;
    }

    public String authenticateAppAndGetServiceToken(String appToken)
            throws SecretsManagerException, AuthenticationException {
        ClientToken clientToken = new ClientToken(appToken);
        clientToken.decode();
        ClientTokenVerifier clientTokenVerifier = new ClientTokenVerifier();
        clientTokenVerifier.verify(clientToken);
        return AuthenticationToken.encodeGuestToken(hashClientId(clientToken.getClientId()));
    }

}
