package com.profiles.app.info;

public class ResponseInfo {
	
	private Object data;
	
	public ResponseInfo() {
		
	}
	
	public ResponseInfo( Object data) {
		super();
		
		this.data = data;
	
	}
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

	
	

}
