package com.infobip.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * GlobalControllerExceptionHandler class handles exceptions for all the
 * controllers for the application.
 * 
 * @author Harshit.Saklecha09@gmail.com
 *
 */
@RestControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

	/**
	 * Method handling InfobipException thrown.
	 * 
	 * @param req
	 *            HttpServeletRequest object for a request
	 * @param response
	 *            HttpServletResponse object for a response
	 * @param e
	 *            object of type InfobipException
	 * @return ResponseEntity object with details about the exception
	 */
	@ExceptionHandler(InfobipException.class)
	protected ResponseEntity<String> handleUcbosException(HttpServletRequest req, HttpServletResponse response,
			InfobipException e) {

		if (e.getStatus() == HttpStatus.BAD_REQUEST.value()) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} else if (e.getStatus() == HttpStatus.NOT_FOUND.value()) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Method handling unhandled exceptions and errors
	 * 
	 * @param req
	 *            HttpServeletRequest object for a request
	 * @param response
	 *            HttpServletResponse object for a response
	 * @param e
	 *            object of type Throwable
	 * @return ResponseEntity object with details about the exception
	 */
	@ExceptionHandler(Throwable.class)
	protected ResponseEntity<Object> handleAllException(HttpServletRequest req, HttpServletResponse response,
			Exception e) {
		LOGGER.error("Internal error in application, please contact the admin");
		return new ResponseEntity<>("Internal error in application, please contact the admin.",
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}