package neowise.user.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

import neowise.user.service.common.ErrorCode;

@JsonInclude(Include.NON_ABSENT)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("error")
public class ErrorDTO {
    private ErrorCode code;
    private String message;

    public ErrorDTO() {
    }

    public ErrorDTO(ErrorCode code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @return String return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return ErrorCode return the code
     */
    public ErrorCode getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(ErrorCode code) {
        this.code = code;
    }

}
