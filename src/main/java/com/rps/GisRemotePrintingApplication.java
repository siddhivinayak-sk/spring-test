package com.rps;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.rps.batch.BatchConfig;
import com.rps.jedis.JedisConfig;
import com.rps.kafka.KafkaConfig;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//--spring.profiles.active=dev


@SpringBootApplication
@Configuration
//@Import(value = {WebConfig.class, BatchConfig.class, KafkaConfig.class})
@Import(value = {WebConfig.class, JpaConfig.class, JedisConfig.class})
@EnableSwagger2 //Enable Swagger in application: /swagger-ui.html
//@EnableScheduling //Enable Cron Scheduler
//@EnableEurekaClient //Enable Eureka Discovery Client for registering
@EnableHystrix //Enable Hystrix for giving fallback for rest apis
@EnableJpaRepositories(basePackages = "com.rps")
public class GisRemotePrintingApplication 
//extends SpringBootServletInitializer 
implements ApplicationRunner, CommandLineRunner {

	//public GisRemotePrintingApplication() {
	//	super();
	//	setRegisterErrorPageFilter(false);
	//}
	
	
	@Value("${appId}")
	private String appId;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	
	
	
	@Override //For war deployment
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(GisRemotePrintingApplication.class);
//	}
	
	//Runs after tomcat starts
	public void run(String...args) {
		System.out.println(args);
	}
	
	//Runs after boot app starts 
	public void run(ApplicationArguments args) {
		System.out.println(args);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(GisRemotePrintingApplication.class, args);
	}

	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				
				.useDefaultResponseMessages(false)                                   
				.globalResponseMessage(RequestMethod.GET,
				  Arrays.asList(new ResponseMessageBuilder()   
				    .code(500)
				    .message("500 Internal Server Error")
				    .responseModel(new ModelRef("string"))
				    .build(),
				    new ResponseMessageBuilder() 
				      .code(403)
				      .message("Forbidden!")
				      .build()))
				.apiInfo(apiInfo())
				;
	}
	
	
	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "GIS Print Service Microservice", 
	      "This is a small printing client installed on cloud.", 
	      "Not for Operation users", 
	      "Terms of service", 
	      new Contact("Sandeep Kumar", "com.sk.hcl", "kumar-sand@hcl.com"), 
	      "License of API", "www.hcl.com", Collections.emptyList());
	}
	
	
}

