package neowise.user.service.config;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;

public class AwsConfig {

        private static final String AWS_ACCESS_KEY_ID = Env.get("AWS_ACCESS_KEY_ID");
        private static final String AWS_SECRET_ACCESS_KEY = Env.get("AWS_SECRET_ACCESS_KEY");

        public static final AwsBasicCredentials AWS_CREDENTIALS = AwsBasicCredentials.create(AWS_ACCESS_KEY_ID,
                        AWS_SECRET_ACCESS_KEY);

        public static final StaticCredentialsProvider AWS_CREDENTIALS_PROVIDER = StaticCredentialsProvider
                        .create(AWS_CREDENTIALS);

        public static final Region AWS_DEFAULT_REGION = Region.AP_SOUTH_1;

}
