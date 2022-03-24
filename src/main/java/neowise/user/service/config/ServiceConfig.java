package neowise.user.service.config;

import java.util.UUID;

public class ServiceConfig {

    public static final String DEPLOYMENT_ID = UUID.randomUUID().toString();

    public static String SERVICE_ENDPOINT = "/user-cicd-test";
}
