package com.javacreed.examples.sc.part3;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

  public static void main(final String[] args) {
    final String xmlFile = "META-INF/spring/app-context.xml";
    try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(xmlFile)) {

      final MembersService service = context.getBean(MembersService.class);

      // Load member with id 1
      Member member = service.getMemberWithId(1);
      System.out.println(member);

      // Edit member with id 1
      member = new Member(1, "Joe Vella");
      service.saveMember(member);

      // Load member with id 1 after it was modified
      member = service.getMemberWithId(1);
      System.out.println(member);
    }
  }
}
