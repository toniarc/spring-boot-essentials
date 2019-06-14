package br.com.devdojo.springbootessentials.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.devdojo.springbootessentials.error.ErrorDetail;
import br.com.devdojo.springbootessentials.error.ResourceNotFoundDetails;
import br.com.devdojo.springbootessentials.error.ResourceNotfoundException;
import br.com.devdojo.springbootessentials.error.ValidationErrorDetails;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(ResourceNotfoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotfoundException ex){
		ResourceNotFoundDetails rnfd = ResourceNotFoundDetails.builder()
			.timesamp(new Date().getTime())
			.status(HttpStatus.NOT_FOUND.value())
			.title("Resource not foung")
			.detail(ex.getMessage())
			.developerMessage(ex.getClass().getName())
			.build();
		
		return new ResponseEntity<>(rnfd, HttpStatus.NOT_FOUND);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<br.com.devdojo.springbootessentials.error.FieldError> errors = new ArrayList<>();
		for(FieldError fd : ex.getBindingResult().getFieldErrors()) {
			errors.add(new br.com.devdojo.springbootessentials.error.FieldError(fd.getField(), fd.getDefaultMessage()));
		}
		
		ValidationErrorDetails errorDetails = ValidationErrorDetails.builder()
			.timesamp(new Date().getTime())
			.status(HttpStatus.BAD_REQUEST.value())
			.title("Field validation error")
			.detail("Request contain errors")
			.developerMessage(ex.getClass().getName())
			.erros(errors)
			.build();
		
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	/*
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorDetail rnfd = ErrorDetail.builder()
				.timesamp(new Date().getTime())
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.title("Server error")
				.detail(ex.getMessage())
				.developerMessage(ex.getClass().getName())
				.build();
			
		return new ResponseEntity<>(rnfd, HttpStatus.INTERNAL_SERVER_ERROR);
	}*/
	
}
