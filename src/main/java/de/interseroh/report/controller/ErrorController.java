package de.interseroh.report.controller;

import de.interseroh.report.exception.BirtUnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hhopf on 25.10.15.
 */
@ControllerAdvice
public class ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);


//    @ExceptionHandler(Exception.class)
//	public ModelAndView error(HttpServletRequest request, ModelAndView modelAndView) {
//		modelAndView.addObject("errorCode",
//				request.getAttribute("javax.servlet.error.status_code"));
//		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
//		String errorMessage = null;
//		if (throwable != null) {
//			errorMessage = throwable.getMessage();
//		}
//		modelAndView.addObject("errorMessage", errorMessage);
//		modelAndView.setViewName("/error");
//		return modelAndView;
//	}


    @ExceptionHandler(BirtUnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView unauthorized(HttpServletRequest request, Exception exception) {
        return exception(request,exception);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView exception(HttpServletRequest request, Exception exception) {
        logger.error("Request: " + request.getRequestURL() + " raised " + exception);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMessage", exception.getLocalizedMessage());
        modelAndView.addObject("errorCode", request.getAttribute("javax.servlet.error.status_code"));
        modelAndView.setViewName("/error");
        return modelAndView;

    }
}
