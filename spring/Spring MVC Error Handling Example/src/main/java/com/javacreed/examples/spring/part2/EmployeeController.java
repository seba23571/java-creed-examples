package com.javacreed.examples.spring.part2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/part2")
public class EmployeeController {

  @RequestMapping(value = "/get/{employeeId}")
  public String getEmployee(@PathVariable("employeeId") final long employeeId, final ModelMap mm)
      throws EmployeeNotFoundException {

    if (employeeId != 1L) {
      throw new EmployeeNotFoundException(employeeId);
    }

    final Employee employee = new Employee();
    employee.setId(employeeId);
    employee.setName("Albert Attard");
    employee.setDepartment("Java Creed");
    mm.addAttribute("employee", employee);

    return "employee";
  }

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
