package com.javacreed.examples.spring.part5;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

@Component
public class CentralExceptionHandlerExceptionResolver extends AbstractHandlerExceptionResolver {

  @Override
  protected ModelAndView doResolveException(final HttpServletRequest request, final HttpServletResponse response,
      final Object handler, final Exception exception) {

    final ModelAndView mav = new ModelAndView();
    mav.addObject("exception", exception);

    if (exception instanceof EmployeeNotFoundException) {
      mav.setViewName("employeeNotFound");
    } else {
      mav.setViewName("error");
    }

    return mav;
  }

}
