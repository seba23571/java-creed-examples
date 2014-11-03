/*
 * #%L
 * Why should we use dependency injection?
 * $Id:$
 * $HeadURL$
 * %%
 * Copyright (C) 2012 - 2014 Java Creed
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.javacreed.examples.di.part4;

import com.google.inject.Inject;

public class Person {

  private final MessageService messageService;
  private final WritingService writingService;

  @Inject
  private Person(final MessageService messageService, WritingService writeService) {
    this.messageService = messageService;
    this.writingService = writeService;
  }

  public void greetFriend() {
    messageService.sendMessage("Hello", "Hello my friend :)");
  }
  
  public void writeToFriend() {
    writingService.write("Hello my friend :)");
  }

}
