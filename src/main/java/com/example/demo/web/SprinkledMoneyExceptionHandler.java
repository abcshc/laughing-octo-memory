package com.example.demo.web;

import com.example.demo.sprinkle.exception.*;
import com.example.demo.web.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SprinkledMoneyExceptionHandler {
    @ExceptionHandler(SprinkledMoneyCreatorReceiverSameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse creatorReceiverSameException(SprinkledMoneyCreatorReceiverSameException exception) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(SprinkledMoneyDuplicateReceiveException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    @ResponseBody
    public ErrorResponse duplicateReceiveException(SprinkledMoneyDuplicateReceiveException exception) {
        return new ErrorResponse(HttpStatus.TOO_MANY_REQUESTS.value(), exception.getMessage());
    }

    @ExceptionHandler(SprinkledMoneyExpiredException.class)
    @ResponseStatus(HttpStatus.GONE)
    @ResponseBody
    public ErrorResponse expiredException(SprinkledMoneyExpiredException exception) {
        return new ErrorResponse(HttpStatus.GONE.value(), exception.getMessage());
    }

    @ExceptionHandler(SprinkledMoneyMinAmountPerCountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse minAmountPerCountException(SprinkledMoneyMinAmountPerCountException exception) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(SprinkledMoneyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse notFoundException(SprinkledMoneyNotFoundException exception) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler(SprinkledMoneyTokenCountOutOfBoundsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse tokenCountOutOfBoundsException(SprinkledMoneyTokenCountOutOfBoundsException exception) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }
}
