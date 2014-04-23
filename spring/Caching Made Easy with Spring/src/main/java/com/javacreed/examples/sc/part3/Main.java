/**
 * Copyright 2012-2014 Java Creed.
 * 
 * Licensed under the Apache License, Version 2.0 (the "<em>License</em>");
 * you may not use this file except in compliance with the License. You may 
 * obtain a copy of the License at: 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 */
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

      // Load member with id 1 again
      member = service.getMemberWithId(1);
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
