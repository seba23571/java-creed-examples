package com.javacreed.examples.spring.part2;

public class Employee {

  private long id;
  private String name;
  private String department;

  public String getDepartment() {
    return department;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setDepartment(final String department) {
    this.department = department;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public void setName(final String name) {
    this.name = name;
  }

}
