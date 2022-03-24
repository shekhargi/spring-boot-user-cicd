package neowise.user.service.exceptions;

import neowise.user.service.common.ErrorCode;

public class AuthorizationException extends ServiceException {

    public AuthorizationException(String message) {
        super(message, ErrorCode.AUTHORIZATION_ERROR);
    }

    public AuthorizationException() {
        this("you are not authorized to perform this action");
    }

}