package com.massiveinfinity.slidingmenu.util;
public class NotificationDTO {
	String message;
	String date;
	boolean flag;
	int id;
	
	public NotificationDTO(String message, String date, boolean flag) {
		super();
		this.message = message;
		this.date = date;
		this.flag = flag;
	}
	
	public NotificationDTO() {
		// TODO Auto-generated constructor stub
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDate() {
		return date;
	}
	public int getId() {
		return id;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}


}
