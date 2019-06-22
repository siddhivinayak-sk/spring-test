package com.rps.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.print.DocFlavor;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Sides;

import org.springframework.stereotype.Service;

import com.ibm.icu.util.Calendar;

@Service
public class PrintServiceImp implements PrintService {

	@Override
	public Date today() {
		return Calendar.getInstance().getTime();
	}

	@Override
	public List<String> getPrinterList() {
        DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
        PrintRequestAttributeSet patts = new HashPrintRequestAttributeSet();
        javax.print.PrintService[] ps = PrintServiceLookup.lookupPrintServices(flavor, patts);
        return Stream.of(ps).map( p -> p.getName()).collect(Collectors.toList());
	}

	@Override
	public List<String> getActualPrinterList() {
        DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
        PrintRequestAttributeSet patts = new HashPrintRequestAttributeSet();
        patts.add(Sides.DUPLEX);
        javax.print.PrintService[] ps = PrintServiceLookup.lookupPrintServices(flavor, patts);
        return Stream.of(ps).map( p -> p.getName()).collect(Collectors.toList());
	}

}
