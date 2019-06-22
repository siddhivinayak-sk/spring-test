package com.rps.services;

import java.util.Date;
import java.util.List;

public interface PrintService {
	public Date today();
	
	public List<String> getPrinterList();
	
	public List<String> getActualPrinterList();
	
}
