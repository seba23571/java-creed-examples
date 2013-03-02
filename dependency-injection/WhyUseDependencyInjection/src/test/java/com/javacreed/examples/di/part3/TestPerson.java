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
