package com.rps.controllers;

import java.awt.GraphicsEnvironment;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jasper.report.beans.JasperPage;
import com.jasper.report.beans.JasperReport;
import com.rps.model.Report;

import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@RestController
@RequestMapping("/jasper")
public class JasperRestController {

	@Value("${template.repo.path:}")
	String templatePath;
	
	@GetMapping("/attribs")
	public JasperPage attribs() {
		JasperPage jp = new JasperPage();
		jp.setFontList(Stream.of(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()).collect(Collectors.toList()));
		
		
		
		return jp;
	}
	
	
	
	@RequestMapping(value = "/read2", produces = {"application/JSON"})
	public JasperReport readFileDetails2(@RequestParam("filename") String fileName) throws Exception {
		JasperReport r = new JasperReport();
		String templateFile = templatePath + File.separator + fileName;
		
		byte bytes[] = Files.readAllBytes(Paths.get(templateFile));
		JAXBContext context = JAXBContext.newInstance(JasperReport.class);
		
		String detail = new String(bytes);
		if(null != detail && !"".equals(detail)) {
			//detail = detail.replaceAll("<jasperReport(.*?)>", "<jasperReport>");
			detail = detail.replaceAll("xmlns=\"(.*?)\"", "");
			detail = detail.replaceAll("xmlns:xsi=\"(.*?)\"", "");
			detail = detail.replaceAll("xsi:schemaLocation=\"(.*?)\"", "");
			detail = detail.replace("<jr:", "<");
			detail = detail.replace("<jr:", "<");
			detail = detail.replace("</jr:", "</");
			detail = detail.replaceAll("<table (.*?)>", "<table>");
			Unmarshaller um = context.createUnmarshaller();
			InputStream is = new ByteArrayInputStream(detail.getBytes());
			r = (JasperReport)um.unmarshal(is);
			r.setFileName(fileName);
		}	
		return r;
	}
	
	@RequestMapping(value = "/write2", produces = {"application/JSON"})
	public JasperReport writeFileDetails2(@RequestBody JasperReport report) {
		try {
			if(null != report && null != report.getFileName()) {
				String templateFile = report.getFileName();
				String modifiedTemplateFile = new Date().getTime() + report.getFileName();
				String newTemplateFile = report.getNewFileName();
				JAXBContext context = JAXBContext.newInstance(JasperReport.class);
				
				byte ibytes[] = Files.readAllBytes(Paths.get(templatePath + File.separator + templateFile));
				String idata = new String(ibytes);
				Marshaller m = context.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				StringWriter sw = new StringWriter();
				m.marshal(report, sw);
				String minput = sw.toString();
				minput = getDetails(minput);
				if(null != minput && !"".equals(minput)) {
					minput = minput.replace("&amp;", "&");
					minput = minput.replace("&lt;", "<");
					minput = minput.replace("&gt;", ">");
					minput = minput.replace("<table>", "<jr:table xmlns:jr=\"http://jasperreports.sourceforge.net/jasperreports/components\" xsi:schemaLocation=\"http://jasperreports.sourceforge.net/jasperreports/components http://jasperreports.sourceforge.net/xsd/components.xsd\">");
					minput = minput.replace("</table>", "</jr:table>");
					minput = minput.replace("<column", "<jr:column");
					minput = minput.replace("</column>", "</jr:column>");
					idata = idata.replace(getDetails(idata), minput);
				}
				
				if(!new File(templatePath + "/compiled").exists()) {
					Files.createDirectories(Paths.get(templatePath + "/compiled"));
				}
				
				String tempFile = templatePath + "/compiled/temp.jrxml";
				String tempCompileFile = templatePath + "/compiled/temp.jasper";
				String deleteExistingCopiledFile = templatePath + "/compiled/" + report.getFileName(); 
				Files.deleteIfExists(Paths.get(deleteExistingCopiledFile));
				Files.deleteIfExists(Paths.get(tempFile));
				Files.deleteIfExists(Paths.get(tempCompileFile));
				Files.write(Paths.get(tempFile), idata.getBytes(), StandardOpenOption.CREATE_NEW);
				JasperCompileManager.compileReportToFile(tempFile, tempCompileFile);
				
				String fileNameToSave = "";
				if(report.isSaveas()) {
					if(null != newTemplateFile && !"".equals(newTemplateFile)) {
						fileNameToSave = newTemplateFile;
					}
					else {
						fileNameToSave = modifiedTemplateFile;
					}
				}
				else {
					fileNameToSave = templateFile;
				}
				Files.deleteIfExists(Paths.get(templatePath + File.separator + "/compiled/" +fileNameToSave));
				Files.deleteIfExists(Paths.get(templatePath + File.separator + fileNameToSave));
				Files.write(Paths.get(templatePath + File.separator + fileNameToSave), idata.getBytes(), StandardOpenOption.CREATE_NEW);
			}
		}
		catch(Exception ex) {
			report.setIserror(true);
			report.setErrormessage(ex.getMessage());
		}
		return report;
	}
	
	
	public void generateTestReport() throws Exception {
		
	}
	
	
	
	private String getDetails(String data) {
		Matcher m = Pattern.compile("<detail>(.*?)</detail>", Pattern.DOTALL).matcher(data);
		return (m.find())?m.group():"";
	}	
	
	@RequestMapping("/read")
	public Report readFileDetails(@RequestParam("filename") String fileName) throws Exception {
		Report r = new Report();
		
		String templateFile = templatePath + File.separator + fileName;
		JasperDesign jd = JRXmlLoader.load(new File(templateFile));
		JRBand[] bands = jd.getAllBands();
		r.setFileName(fileName);
		
		r.getProperties().put("width", jd.getPageWidth());
		r.getProperties().put("height", jd.getPageHeight());
		r.getProperties().put("leftMargin", jd.getLeftMargin());
		r.getProperties().put("rightMargin", jd.getRightMargin());
		r.getProperties().put("topMargin", jd.getTopMargin());
		r.getProperties().put("bottomMargin", jd.getBottomMargin());
		r.getProperties().put("columnWidth", jd.getColumnWidth());
		
		
		
		
		int bandHeight = 0;
		for(JRBand band:bands) {
			for(JRElement element:band.getElements()) {
				
				
				Report.Field field = new Report.Field();
				field.setBandHeight(bandHeight);
				field.setHeight(element.getHeight());
				field.setWidth(element.getWidth());
				field.setKey(element.getKey());
				field.setUuid(element.getUUID().toString());
				field.setX(element.getX());
				field.setY(element.getY());
				r.getFieldList().add(field);
			}
			bandHeight = bandHeight + band.getHeight();
		}
		
		return r;
	}
	
	@RequestMapping("/write")
	public Report writeFileDetails(@RequestBody Report report) throws Exception {
		if(null != report && null != report.getFileName() && null != report.getFieldList()) {
			String templateFile = templatePath + File.separator + report.getFileName();
			String modifiedTemplateFile = templatePath + File.separator + new Date().getTime() + report.getFileName();
			byte[] filedata = Files.readAllBytes(Paths.get(templateFile));
			String data = new String(filedata);
			for(Report.Field field:report.getFieldList()) {
				Matcher m = Pattern.compile("<reportElement (.*?) uuid=\"" + field.getUuid() + "\"").matcher(data);
				while(m.find()) {
					String line = m.group();
					Matcher m1 = Pattern.compile("x=\"([0-9]+)\"").matcher(line);
					Matcher m2 = Pattern.compile("y=\"([0-9]+)\"").matcher(line);
					Matcher m3 = Pattern.compile("width=\"([0-9]+)\"").matcher(line);
					Matcher m4 = Pattern.compile("height=\"([0-9]+)\"").matcher(line);
					m1.find();
					m2.find();
					m3.find();
					m4.find();
					String x = m1.group(1);
					String y = m2.group(1);
					String width = m3.group(1);
					String height = m4.group(1);
					String newLine = new String(line);
					newLine = newLine.replaceAll("x=\"" + x + "\"", "x=\"" + field.getX() + "\"");
					newLine = newLine.replaceAll("y=\"" + y + "\"", "y=\"" + field.getY() + "\"");
					newLine = newLine.replaceAll("width=\"" + width + "\"", "width=\"" + field.getWidth() + "\"");
					newLine = newLine.replaceAll("height=\"" + height + "\"", "height=\"" + field.getHeight() + "\"");
					data = data.replace(line, newLine);
				}
			}
			Files.write(Paths.get(modifiedTemplateFile), data.getBytes(), StandardOpenOption.CREATE);
		}
		return report;
	}
	
	
}
