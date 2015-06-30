/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * 
 * (c) 2015 - Interseroh
 */
package de.interseroh.report.webconfig;

import java.io.File;

import nz.net.ultraq.thymeleaf.LayoutDialect;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.spring3.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import de.interseroh.report.services.BirtReportService;

@Configuration
@EnableWebMvc
@ComponentScan({ "de.interseroh.report.controller",
		"de.interseroh.report.model" })
@PropertySource({ "classpath:report-config.properties" })
@Import({ ReportConfig.class })
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	private static Logger logger = Logger.getLogger(WebMvcConfig.class);

	@Autowired
	private Environment environment;

	@Override
	public void configureContentNegotiation(
			ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(false);
		configurer.ignoreAcceptHeader(false);
		configurer.defaultContentType(MediaType.TEXT_PLAIN);

		configurer.mediaType("html", MediaType.TEXT_HTML);
		configurer.mediaType("xml", MediaType.APPLICATION_XML);
	}

	@Bean
	public ViewResolver thymeleafViewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setOrder(1);
		viewResolver.setExcludedViewNames(new String[] { "*.xml" });
		return viewResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		templateEngine.addDialect(layoutDialect());
		templateEngine.addDialect(springSecurityDialect());
		return templateEngine;
	}

	@Bean
	public IDialect layoutDialect() {
		return new LayoutDialect();
	}

	@Bean
	public IDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}

	@Bean
	public ServletContextTemplateResolver templateResolver() {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix("/views");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCacheable(false);
		return templateResolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**") //
				.addResourceLocations("/resources/");
		registry.addResourceHandler("/images/**") //
				.addResourceLocations("/images/");

		// FIXME idueppe - not dry.. see BirtReportService
		String defaultDirectory = environment.getProperty("java.io.tmpdir");
		String baseImageURL = environment
				.getProperty(BirtReportService.REPORT_BASE_IMAGE_URL_KEY);
		String imageDirectory = "file://"
				+ environment.getProperty(
						BirtReportService.REPORT_IMAGE_DIRECTORY_KEY,
						defaultDirectory);

		logger.info("\tBaseImageUrl:   " + baseImageURL);
		logger.info("\tImageDirectory: "
				+ ensureTrailingSeparator(imageDirectory));

		registry.addResourceHandler(baseImageURL + "/**").addResourceLocations(
				ensureTrailingSeparator(imageDirectory));
	}

	private String ensureTrailingSeparator(String imageDirectory) {
		if (imageDirectory.charAt(imageDirectory.length() - 1) != File.separatorChar) {
			imageDirectory = imageDirectory + "/";
		}
		return imageDirectory;
	}

}
