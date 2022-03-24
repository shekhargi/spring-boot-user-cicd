package neowise.user.service.config;

public class Env {

    private static EnvHelper envHelper;

    static {
        envHelper = new EnvHelper();
    }

    public static String get(String key) {
        return envHelper.get(key);
    }
}
