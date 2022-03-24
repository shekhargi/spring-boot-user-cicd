package neowise.user.service.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import neowise.user.service.dto.ErrorDTO;
import neowise.user.service.exceptions.ServiceException;

@RestControllerAdvice
public class CommonControllerAdvice {

    @ExceptionHandler({ Exception.class })
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDTO> handleGeneralException(Exception e) {
        e.printStackTrace();
        return handleGeneralException(new ServiceException());
    }

}
