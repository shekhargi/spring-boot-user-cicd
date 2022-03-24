package neowise.user.service.constants;

public enum EnvType {

    DEV("dev"), //
    TEST("test"), //
    STAGING("staging"), //
    PROD("prod"); //

    private String env;

    EnvType(String env) {
        this.env = env;
    }

    @Override
    public String toString() {
        return env;
    }
}
