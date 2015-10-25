package de.interseroh.report.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hhopf on 25.10.15.
 */
@Controller
public class ErrorController {

	@RequestMapping("/error.html")
	public ModelAndView error(HttpServletRequest request, ModelAndView modelAndView) {
		modelAndView.addObject("errorCode",
				request.getAttribute("javax.servlet.error.status_code"));
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		String errorMessage = null;
		if (throwable != null) {
			errorMessage = throwable.getMessage();
		}
		modelAndView.addObject("errorMessage", errorMessage);
		modelAndView.setViewName("/error");
		return modelAndView;
	}
}
