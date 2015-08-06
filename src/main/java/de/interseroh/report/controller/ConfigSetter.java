package de.interseroh.report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@PropertySource({ "classpath:config.properties", "classpath:version.properties" })
@Component
public class ConfigSetter {

	@Autowired
	private Environment env;

	public void setBranding(ModelAndView modelAndView) {
		String brandingText = env
				.getProperty("text.branding", "Report Cockpit");
		modelAndView.addObject("brandingText", brandingText);
		String brandingLogo = env.getProperty("logo.branding", "birt-logo.png");
		modelAndView.addObject("brandingLogo", brandingLogo);
	}

	public void setVersion(ModelAndView modelAndView) {
		String version = env.getProperty("version");
		modelAndView.addObject("version", version);
	}

}
