package neowise.user.service.exceptions;

import org.springframework.http.HttpStatus;

import neowise.user.service.common.ErrorCode;
import neowise.user.service.dto.ErrorDTO;

public class ServiceException extends Exception {
    private final static HttpStatus DEFAULT_STATUS = HttpStatus.SERVICE_UNAVAILABLE;

    private ErrorCode code;
    private Exception systemException;
    private String errorMessage;
    private HttpStatus httpStatus;

    public ServiceException(String errorMessage, ErrorCode code, HttpStatus httpStatus) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public ServiceException(String errorMessage, ErrorCode code) {
        this(errorMessage, code, DEFAULT_STATUS);
    }

    public ServiceException(String errorMessage) {
        this(errorMessage, ErrorCode.SERVICE_FAILURE);
    }

    public ServiceException() {
        this("Service is currently not available.");
    }

    /**
     * @return ErrorCode return the code
     */
    public ErrorCode getCode() {
        return this.code;
    }

    public ErrorDTO unpack() {
        return new ErrorDTO(code, this.errorMessage);
    }

    public void setSystemException(Exception e) {
        this.systemException = e;
    }

    public void logSystemException() {
        if (this.systemException != null) {
            this.systemException.printStackTrace();
        }
    }

    /**
     * @param code the code to set
     */
    public void setCode(ErrorCode code) {
        this.code = code;
    }

    /**
     * @return Exception return the systemException
     */
    public Exception getSystemException() {
        return systemException;
    }

    /**
     * @return String return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return HttpStatus return the httpStatus
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    /**
     * @param httpStatus the httpStatus to set
     */
    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

}
