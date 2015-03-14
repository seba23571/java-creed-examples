package com.javacreed.examples.spring.part3;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/part3")
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

}
