package com.psoft.match.tcc.util.exception;

import com.psoft.match.tcc.util.exception.common.BadRequestException;
import com.psoft.match.tcc.util.exception.common.ConflictException;
import com.psoft.match.tcc.util.exception.common.ForbiddenException;
import com.psoft.match.tcc.util.exception.common.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ApiControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomErrorType> handleNotFoundException(RuntimeException exception, HttpServletRequest req) {
        return this.buildErrorResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<CustomErrorType> handleAlreadyExistsException(RuntimeException exception, HttpServletRequest req) {
        return this.buildErrorResponse(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CustomErrorType> handleNoOneLoggedException(RuntimeException exception, HttpServletRequest req) {
        return this.buildErrorResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<CustomErrorType> handleUnauthorizedException(RuntimeException exception, HttpServletRequest req) {
        return this.buildErrorResponse(exception, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<CustomErrorType> handleForbiddenException(RuntimeException exception, HttpServletRequest req) {
        return this.buildErrorResponse(exception, HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<CustomErrorType> buildErrorResponse(RuntimeException exception, HttpStatus statusCode) {
        CustomErrorType error = new CustomErrorType(exception.getMessage());
        return new ResponseEntity(error, statusCode);
    }
}
