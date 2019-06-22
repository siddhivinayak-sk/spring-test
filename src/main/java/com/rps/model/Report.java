package com.rps.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Report implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String fileName;
	private Map<String, Object> properties;
	private List<Field> fieldList;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Map<String, Object> getProperties() {
		if(null == properties) {
			properties = new HashMap<String, Object>();
		}
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public List<Field> getFieldList() {
		if(null == fieldList) {
			fieldList = new ArrayList<Field>();
		}
		return fieldList;
	}

	public void setFieldList(List<Field> fieldList) {
		this.fieldList = fieldList;
	}





	public static class Field implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String fieldType;
		private String uuid;
		private String key;
		private int x;
		private int y;
		private int width;
		private int height;
		private Map<String, Object> properties;
		private int bandHeight; 
		
		
		public String getFieldType() {
			return fieldType;
		}
		public void setFieldType(String fieldType) {
			this.fieldType = fieldType;
		}
		public String getUuid() {
			return uuid;
		}
		public void setUuid(String uuid) {
			this.uuid = uuid;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		public int getHeight() {
			return height;
		}
		public void setHeight(int height) {
			this.height = height;
		}
		public int getBandHeight() {
			return bandHeight;
		}
		public void setBandHeight(int bandHeight) {
			this.bandHeight = bandHeight;
		}
		public Map<String, Object> getProperties() {
			if(null == properties) {
				properties = new HashMap<String, Object>();
			}
			return properties;
		}
		public void setProperties(Map<String, Object> properties) {
			this.properties = properties;
		}
	}

}
