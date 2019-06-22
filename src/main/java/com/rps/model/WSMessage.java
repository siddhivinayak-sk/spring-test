package com.rps.model;

import java.io.Serializable;

public class WSMessage implements Serializable {
	final static public long serialVersionUID = 1L;
	
	private String from;
	private String text;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
	
	
}
