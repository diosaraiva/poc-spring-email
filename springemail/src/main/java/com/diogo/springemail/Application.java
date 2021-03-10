package com.diogo.springemail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application extends SpringBootServletInitializer
{
	static final Logger LOG = LoggerFactory.getLogger(Application.class);

	private static ApplicationContext applicationContext;

	public static void main(String[] args) 
	{
		SpringApplication.run(Application.class, args);

		//BeanUtil.displayAllBeans(applicationContext);
	}
}