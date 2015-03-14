package com.javacreed.examples.spring.part3;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CentralControllerExceptionHandler {

  @ExceptionHandler({ EmployeeNotFoundException.class })
  public ModelAndView handleEmployeeError(final EmployeeNotFoundException e) {
    final ModelAndView mav = new ModelAndView();
    mav.addObject("exception", e);
    mav.setViewName("employeeNotFound");
    return mav;
  }

  @ExceptionHandler({ Exception.class })
  public ModelAndView handleError(final Exception e) {
    final ModelAndView mav = new ModelAndView();
    mav.addObject("exception", e);
    mav.setViewName("error");
    return mav;
  }

}
