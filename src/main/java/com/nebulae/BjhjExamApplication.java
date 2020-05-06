package com.nebulae;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@ServletComponentScan
@SpringBootApplication
@MapperScan("com.nebulae.*.mapper")
public class BjhjExamApplication extends SpringBootServletInitializer{
	
	 @Override
	 protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	        // 注意这里要指向原先用main方法执行的Application启动类
	        return builder.sources(BjhjExamApplication.class);
	 }
	
	
	public static void main(String[] args) {
		SpringApplication app=new SpringApplication(BjhjExamApplication.class);
		//屏蔽banner
		//app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}
	

}
