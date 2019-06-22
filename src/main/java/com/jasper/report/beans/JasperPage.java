package com.jasper.report.beans;

import java.io.Serializable;
import java.util.List;

public class JasperPage implements Serializable {
	private final static long serialVersionUID = 1L;
	
	private List<String> fontList;
	
	
	

	public List<String> getFontList() {
		return fontList;
	}

	public void setFontList(List<String> fontList) {
		this.fontList = fontList;
	}
	
	

}
