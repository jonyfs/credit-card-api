package br.com.jonyfs.credit.card.api.controller;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.jonyfs.credit.card.api.binding.ErrorResource;
import br.com.jonyfs.credit.card.api.binding.FieldErrorResource;
import br.com.jonyfs.credit.card.api.exceptions.EntityNotFoundException;
import br.com.jonyfs.credit.card.api.exceptions.InvalidRequestException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    public static final Logger LOGGER = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    protected <T> ResponseEntity<T> response(T body, HttpStatus status) {
        LOGGER.debug("response with status {}", status);
        return new ResponseEntity<>(body, new HttpHeaders(), status);
    }

    protected ResponseEntity<ErrorResource> errorResponse(Throwable throwable, HttpStatus status, HttpServletRequest request) {
        if (null != throwable) {
            ErrorResource errorResource = new ErrorResource(throwable);
            LOGGER.error("erro " + errorResource.getCode() + " capturado para ip " + request.getRemoteAddr() + " ==> " + errorResource, throwable);
            return response(errorResource, status);
        }
        else {
            LOGGER.error("error for ip " + request.getRemoteAddr() + " ==> ", status);
            return response(null, status);
        }
    }

    protected ResponseEntity<ErrorResource> errorResponse(Throwable throwable, List<FieldErrorResource> fieldErrors, HttpStatus status, HttpServletRequest request) {
        if (null != throwable) {
            ErrorResource errorResource = new ErrorResource(throwable, fieldErrors);
            LOGGER.error("error " + errorResource.getCode() + " for ip " + request.getRemoteAddr() + " ==> " + errorResource, throwable);
            return response(errorResource, status);
        }
        else {
            LOGGER.error("erro for ip " + request.getRemoteAddr() + " ==> ", status);
            return response(null, status);
        }
    }

    @ExceptionHandler({ InvalidRequestException.class })
    @ResponseBody
    protected ResponseEntity<ErrorResource> handleInvalidRequest(RuntimeException e, HttpServletRequest request) {
        InvalidRequestException ire = (InvalidRequestException) e;
        List<FieldErrorResource> fieldErrorResources = new ArrayList<FieldErrorResource>();
        List<FieldError> fieldErrors = ire.getErrors().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            FieldErrorResource fieldErrorResource = new FieldErrorResource(fieldError.getField(), fieldError.getCode(), fieldError.getDefaultMessage());
            fieldErrorResources.add(fieldErrorResource);
        }

        return errorResponse(e, fieldErrorResources, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler({ EntityNotFoundException.class })
    @ResponseBody
    protected ResponseEntity<ErrorResource> handleEntityNotFound(RuntimeException e, HttpServletRequest request) {
        return errorResponse(e, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ AccessDeniedException.class })
    @ResponseBody
    protected ResponseEntity<ErrorResource> handleAccessDenied(RuntimeException e, HttpServletRequest request) {
        return errorResponse(e, HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler({ MaxUploadSizeExceededException.class })
    @ResponseBody
    protected ResponseEntity<ErrorResource> handleMaxUploadSizeExceeded(RuntimeException e, HttpServletRequest request) {
        return errorResponse(e, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResource> handleError(Exception e, HttpServletRequest request) {
        return errorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}