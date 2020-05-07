package com.nebulae.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.nebulae.utils.AESUtil;


@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
	
	@Autowired
	UpLoadFileConfig upLoadFileConfig;
	
	 @Override
	 public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("/**")
	                .addResourceLocations("classpath:/META-INF/resources/")
	                .addResourceLocations("classpath:/resources/")
	                .addResourceLocations("classpath:/static/")
	                .addResourceLocations("classpath:/public/");
	        registry.addResourceHandler("/files/**").addResourceLocations("file:///"+upLoadFileConfig.getUploadPath());
	        super.addResourceHandlers(registry);
	    }
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(AESUtil.AESDecode("9K4IijxVFBAbV+IOyWpeWQ==")).setViewName(AESUtil.AESDecode("BGL/PJQo+37xahji7nvnNw=="));
        registry.addViewController(AESUtil.AESDecode("YiRMwGfmVCcRDNAK60n1cg==")).setViewName(  AESUtil.AESDecode("EHRoZtb3JzcT2sAUMKY7HQ=="));
	}

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//    	registry.addInterceptor(new LicenseCheckInterceptor()).addPathPatterns(AESUtil.AESDecode("YiRMwGfmVCcRDNAK60n1cg=="));
        super.addInterceptors(registry);
    }

	


}
