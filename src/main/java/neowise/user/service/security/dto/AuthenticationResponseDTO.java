package neowise.user.service.security.dto;

import neowise.user.service.dto.response.ServiceResponseDTO;

public class AuthenticationResponseDTO extends ServiceResponseDTO {

    private String serviceToken;

    public AuthenticationResponseDTO() {

    }

    public AuthenticationResponseDTO(String serviceToken) {
        this.serviceToken = serviceToken;
    }

    /**
     * @return String return the serviceToken
     */
    public String getServiceToken() {
        return serviceToken;
    }

    /**
     * @param serviceToken the serviceToken to set
     */
    public void setServiceToken(String serviceToken) {
        this.serviceToken = serviceToken;
    }

}
