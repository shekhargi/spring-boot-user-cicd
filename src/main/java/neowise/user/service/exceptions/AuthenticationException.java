package neowise.user.service.exceptions;

import neowise.user.service.common.ErrorCode;

public class AuthenticationException extends ServiceException {
    public AuthenticationException(String errorMessage) {
        super(errorMessage, ErrorCode.AUTHENTICATION_ERROR);
    }

    public AuthenticationException() {
        this("invalid credentials");
    }
}
