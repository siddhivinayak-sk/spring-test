package com.jasper.report.beans;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "properties",
    "styles",
    "subDatasets",
    "queryStrings",
    "fields",
    "backgroud",
    "details"	  
})
@XmlRootElement(name = "jasperReport")
public class JasperReport implements Serializable {
	private final static long serialVersionUID = 1L;
	
	@XmlElement(name = "property")
	private List<Property> properties;
	
	@XmlElement(name = "style")
	private List<Style> styles;
	
	@XmlElement(name = "subDataset")
	private List<SubDataset> subDatasets;
	
	@XmlElement(name = "queryString")
	private List<QueryString> queryStrings;
	
	@XmlElement(name = "field")
	private List<Field> fields;
	
	@XmlElement(name = "backgroud")
	private Backgroud backgroud;
	
	@XmlElement(name = "detail")
	private List<Detail> details;
	
	@XmlTransient
	private String fileName;

	@XmlTransient
	private String newFileName;
	
	
	@XmlAttribute(name = "name")
	private String name;
	
	@XmlAttribute(name = "pageWidth")
	private int pageWidth;
	
	@XmlAttribute(name = "pageHeight")
	private int pageHeight;
	
	@XmlAttribute(name = "columnWidth")
	private int columnWidth;
	
	@XmlAttribute(name = "leftMargin")
	private int leftMargin;
	
	@XmlAttribute(name = "rightMargin")
	private int rightMargin;
	
	@XmlAttribute(name = "topMargin")
	private int topMargin;
	
	@XmlAttribute(name = "bottomMargin")
	private int bottomMargin;
	
	@XmlAttribute(name = "uuid")
	private String uuid;	

	@XmlTransient
	private boolean saveas;

	@XmlTransient
	private boolean iserror;
	
	@XmlTransient
	private String errormessage;
	
	
	public List<Property> getProperties() {
		return properties;
	}
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
	public List<Style> getStyles() {
		return styles;
	}
	public void setStyles(List<Style> styles) {
		this.styles = styles;
	}
	public List<SubDataset> getSubDatasets() {
		return subDatasets;
	}
	public void setSubDatasets(List<SubDataset> subDatasets) {
		this.subDatasets = subDatasets;
	}
	public List<QueryString> getQueryStrings() {
		return queryStrings;
	}
	public void setQueryStrings(List<QueryString> queryStrings) {
		this.queryStrings = queryStrings;
	}
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	public Backgroud getBackgroud() {
		return backgroud;
	}
	public void setBackgroud(Backgroud backgroud) {
		this.backgroud = backgroud;
	}
	public List<Detail> getDetails() {
		return details;
	}
	public void setDetails(List<Detail> details) {
		this.details = details;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPageWidth() {
		return pageWidth;
	}
	public void setPageWidth(int pageWidth) {
		this.pageWidth = pageWidth;
	}
	public int getPageHeight() {
		return pageHeight;
	}
	public void setPageHeight(int pageHeight) {
		this.pageHeight = pageHeight;
	}
	public int getColumnWidth() {
		return columnWidth;
	}
	public void setColumnWidth(int columnWidth) {
		this.columnWidth = columnWidth;
	}
	public int getLeftMargin() {
		return leftMargin;
	}
	public void setLeftMargin(int leftMargin) {
		this.leftMargin = leftMargin;
	}
	public int getRightMargin() {
		return rightMargin;
	}
	public void setRightMargin(int rightMargin) {
		this.rightMargin = rightMargin;
	}
	public int getTopMargin() {
		return topMargin;
	}
	public void setTopMargin(int topMargin) {
		this.topMargin = topMargin;
	}
	public int getBottomMargin() {
		return bottomMargin;
	}
	public void setBottomMargin(int bottomMargin) {
		this.bottomMargin = bottomMargin;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public boolean isSaveas() {
		return saveas;
	}
	public void setSaveas(boolean saveas) {
		this.saveas = saveas;
	}
	public boolean isIserror() {
		return iserror;
	}
	public void setIserror(boolean iserror) {
		this.iserror = iserror;
	}
	public String getErrormessage() {
		return errormessage;
	}
	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}
	public String getNewFileName() {
		return newFileName;
	}
	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}
	
	
}
