package com.application.common.exception;

import java.util.Date;

public class ErrorMessage {
	private int statusCode;
	private Date timestamp;
	private String message;
	private String description;

	public ErrorMessage(int statusCode, Date timestamp, String message, String description) {
		this.statusCode = statusCode;
		this.setTimestamp(timestamp);
		this.setMessage(message);
		this.setDescription(description);
	}

	public int getStatusCode() {
		return statusCode;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
