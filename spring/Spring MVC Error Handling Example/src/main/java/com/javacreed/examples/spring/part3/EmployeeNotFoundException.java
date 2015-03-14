package com.javacreed.examples.spring.part3;

public class EmployeeNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 7375065024145333700L;

  private final long employeeId;

  public EmployeeNotFoundException(final long employeeId) {
    super("The employee with id: " + employeeId + " was not found!!");
    this.employeeId = employeeId;
  }

  public long getEmployeeId() {
    return employeeId;
  }

}
