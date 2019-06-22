package com.rps.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.rps.model.PrintReady;
import com.rps.model.WSMessage;
import com.rps.services.PrintService;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@RestController
@RequestMapping("/rp")
public class PrintRestController {

	
	@Value("${template.repo.path:}")
	private String templatesDir;

	@RequestMapping("/")
	@CrossOrigin(origins = "http://localhost:8080") //Enable Cross referencing
	public String hello() {
		return "This is the Print Remote service";
	}
	
	@RequestMapping("/{printid}")
	public PrintReady getPrintReady(@PathVariable(name = "printid") String printId) {
		PrintReady pr = new PrintReady();
		pr.setPrintId(printId);
		
		try {
			//Class.forName("org.apache.derby.jdbc.ClientDriver");
			//Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "user", "app");
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=sample", "sa", "p@ssw0rd");
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("tid", "T100001");
			//String sourceJasperXml = "r1.jrxml";
			String source = "r2.jrxml";
			String compileSourceJasper = templatesDir + "/compiled/" + source;
			
			if(!new File(compileSourceJasper).getParentFile().exists()) {
				new File(compileSourceJasper).getParentFile().mkdirs();
			}
	
			JasperCompileManager.compileReportToFile(templatesDir + File.separator + source, compileSourceJasper);
			JasperPrint jp = JasperFillManager.fillReport(compileSourceJasper, param, con);
			JasperExportManager.exportReportToPdfFile(jp, "c:/templates/test.pdf");
			byte[] data = JasperExportManager.exportReportToPdf(jp);
			pr.setData(Base64.getEncoder().encodeToString(data));
			
			
			/* Import png file into database */
			/*
			PreparedStatement ps = con.prepareStatement("update app.spf_details set A_SIGN_IC1 = ?, A_SIGN_IC2 = ?, C_LOGO1 = ?, C_LOGO2 = ? where TID = 'T100002'");
			Blob img1 = con.createBlob();
			Blob img2 = con.createBlob();
			Blob img3 = con.createBlob();
			Blob img4 = con.createBlob();

			FileInputStream fis1 = new FileInputStream("c:/sandeep/sign1.png");
			FileInputStream fis2 = new FileInputStream("c:/sandeep/sign2.png");
			byte[] b1 = new byte[fis1.available()];
			byte[] b2 = new byte[fis2.available()];
			fis1.read(b1);
			fis2.read(b2);
			fis1.close();
			fis2.close();

			img1.setBytes(1, b1);
			img2.setBytes(1, b2);
			img3.setBytes(1, b1);
			img4.setBytes(1, b2);
			
			ps.setBlob(1, img1);
			ps.setBlob(2, img2);
			ps.setBlob(3, img3);
			ps.setBlob(4, img4);
			ps.executeUpdate();
			
			img1.free();
			img2.free();
			img3.free();
			img4.free();
			*/
			
			/*
			PreparedStatement ps2 = con.prepareStatement("select A_SIGN_IC1 from app.SPF_DETAILS where TID = 'T100002'");
			ResultSet rs = ps2.executeQuery();
			int z = 0;
			while(rs.next()) {
				Blob bl1 = rs.getBlob(1);
				FileOutputStream fos = new FileOutputStream("c:/sandeep/" + ++z + ".png");
				fos.write(bl1.getBytes(1, (int)bl1.length()));
				fos.close();
			}
			*/
			
			con.commit();
			con.close();
		}
		catch(Exception ex) {
			pr.setErrorMessage(ex.getMessage());
			ex.printStackTrace();
		}
		return pr;
	}

	
	@RequestMapping("/list/{customerid}")
	public List<PrintReady> getPrintReadyList(@PathVariable(name = "customerid") String customerId) {
		List<PrintReady> prList = new ArrayList<PrintReady>();
		PrintReady pr = new PrintReady();
		pr.setPrintId("10000001");
		prList.add(pr);
		return prList;
	}
	
	@RequestMapping("/message")
	public ResponseEntity<WSMessage> getMessage(@RequestBody WSMessage message) {
		ResponseEntity<WSMessage> res = new ResponseEntity(message, HttpStatus.OK);
		return res;
	}
	
	@RequestMapping("/message1")
	public ResponseEntity<WSMessage> getMessage(@RequestParam(value = "message1", defaultValue = "", required = false) String message) {
		WSMessage ws = new WSMessage();
		ws.setText(message);
		ResponseEntity<WSMessage> res = new ResponseEntity(ws, HttpStatus.OK);
		return res;
	}
	

	//RestTemplate used for making calls to other rest requests
	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping("/message2")
	public String getMessage1(@RequestParam(value = "message1", defaultValue = "", required = false) String message) {
		HttpHeaders header = new HttpHeaders();
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<WSMessage> entity = new HttpEntity<WSMessage>(new WSMessage(), header);
		return restTemplate.exchange("http://localhost:8080/message", HttpMethod.GET, entity, String.class).getBody();
		
	}
	

	//Uploading file to server
	@RequestMapping(value = "/upload", method = {RequestMethod.POST}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String fileUpload(@RequestParam("file") MultipartFile file) throws Throwable {
		File convertedFile = new File("/temp" + file.getOriginalFilename());
		convertedFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convertedFile);
		fos.write(file.getBytes());
		fos.close();
		return "File Uploaded Successfully";
	}


	//Download file
	@RequestMapping(value = "/download", method = {RequestMethod.GET})
	public ResponseEntity<Object> downloadFile() throws Throwable {
		File outfile = new File("/temp/1.png");
		InputStreamResource fis = new InputStreamResource(new FileInputStream(outfile));
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", String.format("attachment; file=\"%s\"", outfile.getName()));
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		
		ResponseEntity<Object> res = ResponseEntity.ok().headers(headers).contentLength(outfile.length()).contentType(MediaType.parseMediaType("application/txt")).body(fis);
		return res;
	}

	
	//Getting service
	@Autowired
	private PrintService pService;
	
	
	
	
}
