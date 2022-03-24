package neowise.user.service.security.authentication;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientToken {

    private String token;
    private String clientId;
    private String clientSecret;
    private String decodedToken;
    private int decodeCount = 0;
    private boolean decodeSuccess = false;

    private Logger logger = LoggerFactory.getLogger(ClientToken.class);

    public ClientToken(String token) {
        this.token = token;
    }

    private String[] getDecodedTokenParts() {
        if (this.decodedToken != null && this.decodedToken.contains(":")) {
            return this.decodedToken.split(":");
        }
        return new String[] {};
    }

    private String getDecodedTokenPart(int i) {
        String[] decodedTokenParts = this.getDecodedTokenParts();

        if (i == 0 || i == 1 && decodedTokenParts.length == 2) {
            return decodedTokenParts[i].trim();
        }
        return null;
    }

    public String getClientId() {
        if (clientId == null && this.decodeCount < 1) {
            logger.warn("clientId is null, call decode() first");
        }
        return clientId;
    }

    public String getClientSecret() {
        if (clientSecret == null && this.decodeCount < 1) {
            logger.warn("clientSecret is null, call decode() first");
        }
        return clientSecret;
    }

    private void decodeClientId() {
        this.clientId = getDecodedTokenPart(0);
    }

    private void decodeClientSecret() {
        this.clientSecret = getDecodedTokenPart(1);
    }

    public void decode() throws IllegalArgumentException {
        if (this.decodeCount > 1 && !this.decodeSuccess) {
            logger.debug("retrying previously failed decode attempt");
        }
        this.decodeCount += 1;
        byte[] decodedBytes = Base64.getDecoder().decode(this.token);
        this.decodedToken = new String(decodedBytes);
        decodeClientId();
        decodeClientSecret();
        String clientId = getClientId();
        String clientSecret = getClientSecret();
        this.decodeSuccess = (clientId != null && clientSecret != null);
    }

}
