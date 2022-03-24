package neowise.user.service.security.common;

public enum AuthRole {
    GUEST_USER("guest"), //
    USER("user");

    private final String key;

    private AuthRole(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return this.key;
    }
}
