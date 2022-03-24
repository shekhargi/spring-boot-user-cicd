package neowise.user.service.security.dto;

public class AuthenticationRequestDTO {

    private String authenticationToken;

    public AuthenticationRequestDTO() {

    }

    /**
     * @return String return the authenticationToken
     */
    public String getAuthenticationToken() {
        return authenticationToken;
    }

    /**
     * @param authenticationToken the authenticationToken to set
     */
    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

}
