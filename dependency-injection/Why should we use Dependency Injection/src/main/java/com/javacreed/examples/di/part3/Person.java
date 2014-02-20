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
package com.javacreed.examples.di.part3;

import com.google.inject.Inject;

public class Person {

  private final MessageService messageService;

  @Inject
  private Person(final MessageService messageService) {
    this.messageService = messageService;
  }

  public void greetFriend() {
    messageService.sendMessage("Hello", "Hello my friend :)");
  }
}
