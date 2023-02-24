package com.application.workmanagement.service.rest.v1;

import org.springframework.http.HttpStatus;

public class ResponseInfo {
	
	private  HttpStatus status;
	private Object data;
	private String message;
	
	public ResponseInfo() {
		
	}
	
	public ResponseInfo(HttpStatus status, Object data, String message) {
		super();
		this.status = status;
		this.data = data;
		this.message = message;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
