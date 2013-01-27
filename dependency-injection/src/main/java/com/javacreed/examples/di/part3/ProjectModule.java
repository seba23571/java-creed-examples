package com.javacreed.examples.di.part3;

import com.google.inject.AbstractModule;

public class ProjectModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(MessageService.class).to(SmsService.class);
  }

}
