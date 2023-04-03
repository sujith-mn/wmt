package com.application.common.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	
	@ExceptionHandler( DemandIdNotFoundException.class )
	public ResponseEntity<ErrorMessage> demandIdNotFoundExceptionHandler(DemandIdNotFoundException exception, WebRequest request) {
		return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), exception.getMessage(),
				request.getDescription(false)),HttpStatus.NOT_FOUND);
	}

//	@ExceptionHandler( ResumeNotFoundException.class )
//	public ResponseEntity<ErrorMessage> resumeNotFoundExceptionHandler(ResumeNotFoundException exception, WebRequest request) {
//		return new ResponseEntity<>(new ErrorMessage(HttpStatus.NO_CONTENT.value(), new Date(), exception.getMessage(),
//				request.getDescription(false)),HttpStatus.NO_CONTENT);
//	}
	@ExceptionHandler( MaxUploadSizeExceededException.class )
	public ResponseEntity<ErrorMessage> demandIdNotFoundExceptionHandler(MaxUploadSizeExceededException exception, WebRequest request) {
		return new ResponseEntity<>(new ErrorMessage(HttpStatus.INSUFFICIENT_STORAGE.value(), new Date(), exception.getMessage(),
				request.getDescription(false)),HttpStatus.INSUFFICIENT_STORAGE);
	}

//	@ExceptionHandler(value = { UsersNotFoundException.class })
//	@ResponseStatus(value = HttpStatus.NOT_FOUND)
//	public ErrorMessage usersNotFoundExceptionHandler(UsersNotFoundException exception, WebRequest request) {
//		logger.error("users not available exception");
//		return new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), exception.getMessage(),
//				request.getDescription(false));
//	}
	@ExceptionHandler(value = { ResumeNotFoundException.class })
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public ErrorMessage exceptionHandeler(ResumeNotFoundException exception, WebRequest request) {
		return new ErrorMessage(HttpStatus.NO_CONTENT.value(), new Date(), exception.getMessage(),
				request.getDescription(false));
	}
	
	@ExceptionHandler(value = { ResumeSizeLimitExceededException.class })
	@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
	public ErrorMessage exceptionHandeler(ResumeSizeLimitExceededException exception, WebRequest request) {
		return new ErrorMessage(HttpStatus.EXPECTATION_FAILED.value(), new Date(), exception.getMessage(),
				request.getDescription(false));
	}
	
	@ExceptionHandler(value = { ResumeAlreadyExistsException.class })
	@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
	public ErrorMessage exceptionHandeler(ResumeAlreadyExistsException exception, WebRequest request) {
		return new ErrorMessage(HttpStatus.EXPECTATION_FAILED.value(), new Date(), exception.getMessage(),
				request.getDescription(false));
	}
	

	@ExceptionHandler(value = { ResumeNotUploadedException.class })
	@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
	public ErrorMessage exceptionHandeler(ResumeNotUploadedException exception, WebRequest request) {
		return new ErrorMessage(HttpStatus.EXPECTATION_FAILED.value(), new Date(), exception.getMessage(),
				request.getDescription(false));
	}

	@ExceptionHandler(value = { Exception.class })
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage exceptionHandeler(Exception exception, WebRequest request) {
		return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), exception.getMessage(),
				request.getDescription(false));
	}

}

























