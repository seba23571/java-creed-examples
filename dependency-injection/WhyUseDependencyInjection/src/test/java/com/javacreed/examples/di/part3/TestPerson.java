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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;

public class TestPerson {
  private Injector injector;

  @Before
  public void init() {
    injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        bind(MockMessageService.class).in(Singleton.class);
        bind(MessageService.class).to(MockMessageService.class);
      }
    });
  }

  @Test
  public void testGreetFriend() {
    final Person person = injector.getInstance(Person.class);
    person.greetFriend();

    final MockMessageService mockService = injector.getInstance(MockMessageService.class);
    Assert.assertEquals("Hello", mockService.subject);
    Assert.assertEquals("Hello my friend :)", mockService.message);
  }

}
