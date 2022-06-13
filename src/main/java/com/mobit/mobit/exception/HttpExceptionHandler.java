package com.mobit.mobit.exception;

import java.net.BindException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mobit.mobit.dto.HttpErrorDTO;

@ControllerAdvice
public class HttpExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		HttpErrorDTO error = HttpErrorDTO.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.message("BAD REQUEST")
				.uriPatch(request.getContextPath())
				.build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<HttpErrorDTO> handleBindException(BindException e, HttpServletRequest request) {
		HttpErrorDTO error = HttpErrorDTO.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.message("BAD REQUEST")
				.uriPatch(request.getRequestURI())
				.build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<HttpErrorDTO> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
		HttpErrorDTO error = HttpErrorDTO.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.message(e.getMessage())
				.developerMessage(e.getLocalizedMessage())
				.uriPatch(request.getRequestURI())
				.build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<HttpErrorDTO> handleNotFoundException(NotFoundException e, HttpServletRequest request) {
		HttpErrorDTO error = HttpErrorDTO.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.NOT_FOUND.value())
				.message(e.getMessage())
				.uriPatch(request.getRequestURI())
				.developerMessage(e.getLocalizedMessage())
				.build();
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
}
