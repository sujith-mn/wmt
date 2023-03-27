package com.application.common.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

//@RestControllerAdvice
//public class GlobalExceptionHandler {
//	@ExceptionHandler(ResourceNotFoundException.class)
//	public String resourceNotFoundException(ResourceNotFoundException ex){
//		String message =ex.getMessage();
//		
//		return message;
//		
//	}
//	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<Map<String ,String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex){
//		Map<String,String> resp = new HashMap<>();
//		ex.getBindingResult().getAllErrors().forEach((error)->{
//			String fieldName =((FieldError)error).getField();
//			String message = error.getDefaultMessage();
//			resp.put(fieldName, message);
//		
//		});
//		return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
//		
//	}
//}   

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	
	@ExceptionHandler( DemandIdNotFoundException.class )
	public ResponseEntity<ErrorMessage> demandIdNotFoundExceptionHandler(DemandIdNotFoundException exception, WebRequest request) {
		return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), exception.getMessage(),
				request.getDescription(false)),HttpStatus.NOT_FOUND);
	}
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

//	@ExceptionHandler(value = { Exception.class })
//	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//	public ErrorMessage exceptionHandeler(Exception exception, WebRequest request) {
//		return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), exception.getMessage(),
//				request.getDescription(false));
//	}

}

























