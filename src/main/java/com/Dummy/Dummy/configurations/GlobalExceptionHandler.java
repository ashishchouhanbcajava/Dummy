package com.Dummy.Dummy.configurations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleExceptions(Exception exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage().toString());
	}

	@ExceptionHandler(org.springframework.security.authorization.AuthorizationDeniedException.class)
	public ResponseEntity<?> handleAuthorizationDenied(Exception ex) {

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You don't have permission to perform this action");
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException x,
			HttpServletResponse response) {
		List<FieldError> fieldErrors = x.getBindingResult().getFieldErrors();
		Map<String, String> errors = new HashMap<>();
		fieldErrors.forEach(e -> {
			errors.put(e.getField(), e.getDefaultMessage());
		});
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
}
