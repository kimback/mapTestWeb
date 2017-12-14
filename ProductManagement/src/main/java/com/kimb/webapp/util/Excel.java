package com.kimb.webapp.util;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class Excel {
	
	private String sender = null;
	private String receiver = null;
	private CommonsMultipartFile file = null;
	
	public Excel() {
	}
	
	public Excel(String sender, String receiver) {
		this.sender = sender;
		this.receiver = receiver;
	}
	
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	public CommonsMultipartFile getFile() {
		return file;
	}

	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}
}
