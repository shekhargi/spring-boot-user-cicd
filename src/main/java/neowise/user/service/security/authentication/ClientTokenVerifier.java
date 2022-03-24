package neowise.user.service.security.authentication;

import com.fasterxml.jackson.databind.JsonNode;

import neowise.user.service.common.JsonUtil;
import neowise.user.service.config.AwsConfig;
import neowise.user.service.config.Env;
import neowise.user.service.exceptions.AuthenticationException;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException;

public class ClientTokenVerifier {

    // private Logger logger = LoggerFactory.getLogger(ClientTokenVerifier.class);

    private final String CREDENTIALS_SECRET_NAME = Env.get("CLIENT_CREDENTIALS_SECRET");
    private final String CLIENT_NAME_KEY = "client_name";
    private final String CLIENT_ID_KEY = "client_id";
    private final String CLIENT_SECRET_KEY = "client_secret";

    private final String CLIENT_NAME = "neowise-app"; // do not change

    private class VerifierMetadata {
        private String clientId;
        private String clientSecret;
        private String clientName;

        public VerifierMetadata(String clientName, String clientId, String clientSecret) {
            this.clientName = clientName;
            this.clientId = clientId;
            this.clientSecret = clientSecret;
        }

        public String getClientName() {
            return clientName;
        }

        public String getClientId() {
            return clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        @Override
        public String toString() {
            return String.format("clientId=%s clientSecret=[%s] clientName=%s", clientId, clientSecret, clientName);
        }
    }

    private VerifierMetadata getVerifierMetaData() {
        SecretsManagerClient secretsClient = SecretsManagerClient.builder().region(AwsConfig.AWS_DEFAULT_REGION)
                .credentialsProvider(AwsConfig.AWS_CREDENTIALS_PROVIDER).build();
        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder().secretId(CREDENTIALS_SECRET_NAME)
                .build();
        GetSecretValueResponse getSecretValueResponse = secretsClient.getSecretValue(getSecretValueRequest);
        String secret = getSecretValueResponse.secretString();
        JsonNode secretJson = JsonUtil.parseJson(secret, JsonNode.class);
        String clientName = secretJson.get(CLIENT_NAME_KEY).asText().trim();
        String clientId = secretJson.get(CLIENT_ID_KEY).asText().trim();
        String clientSecret = secretJson.get(CLIENT_SECRET_KEY).asText().trim();
        VerifierMetadata verifierMetadata = new VerifierMetadata(clientName, clientId, clientSecret);
        return verifierMetadata;
    }

    public void verify(ClientToken clientToken) throws SecretsManagerException, AuthenticationException {
        VerifierMetadata verifierMetadata = getVerifierMetaData();
        String clientId = clientToken.getClientId();
        String clientSecret = clientToken.getClientSecret();

        boolean isClientNameValid = CLIENT_NAME.equals(verifierMetadata.getClientName());
        boolean isClientIdValid = clientId.equals(verifierMetadata.getClientId());
        boolean isClientSecretValid = clientSecret.equals(verifierMetadata.getClientSecret());
        boolean isVerified = isClientNameValid && isClientIdValid && isClientSecretValid;

        if (!isVerified) {
            throw new AuthenticationException();
        }

    }

}
