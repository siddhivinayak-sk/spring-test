package com.rps;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.rps.handlers.RequestHandlerInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.rps")
public class WebConfig extends WebMvcConfigurerAdapter {
	
	//Enable Servlet Handling
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    //Adding interceptors
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/scripts/").addResourceLocations("/scripts/**");
    }    
	
    @Autowired
    private RequestHandlerInterceptor requestHandlerInterceptor; 
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(requestHandlerInterceptor);
    	registry.addInterceptor(localeChangeInterceptor()); //Adding Interceptor for I18N
    }
    

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver irvr = new InternalResourceViewResolver();
        irvr.setPrefix("/WEB-INF/jsps/");
        irvr.setSuffix(".jsp");
        irvr.setOrder(0);
        return irvr;
    }
    
    //Default Locale setting
    @Bean
    public LocaleResolver localeResolver() {
    	SessionLocaleResolver slr = new SessionLocaleResolver();
    	slr.setDefaultLocale(Locale.US);
    	return slr;
    }

    //Locale Change Interceptor
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
    	LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
    	lci.setParamName("locale");
    	return lci;
    }

    //Defining reloadable message object to load local messages
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
    	ReloadableResourceBundleMessageSource s = new ReloadableResourceBundleMessageSource();
    	s.setBasename("classpath:message");
    	s.setDefaultEncoding("UTF-8");
    	return s;
    }
    
    
    
    //Enable global cors referencing from web-page
    @Override
    public void addCorsMappings(CorsRegistry registry) {
    	registry.addMapping("/**").allowedOrigins("**").allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
    }
    
}
