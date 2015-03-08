/*
 * #%L
 * Bulk Mail Java Application
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2012 - 2015 Java Creed
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
package com.javacreed.examples.mail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.swing.SwingWorker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendMailWorker extends SwingWorker<Void, String> {

  public static final Logger LOGGER = LoggerFactory.getLogger(SendMailWorker.class);

  private final List<Email> emails;

  public SendMailWorker(final Collection<Email> emails) {
    this.emails = new ArrayList<>(emails);
  }

  public SendMailWorker(final Email email) {
    emails = Arrays.asList(email);
  }

  @Override
  protected Void doInBackground() throws Exception {

    try {
      final EmailSender emailSender = new EmailSenderImpl();
      for (int i = 0, size = emails.size(); i < size; i++) {
        final Email email = emails.get(i);
        emailSender.send(email);
      }

      onSucceed();
    } catch (final Throwable e) {
      onFail(e);
    }
    return null;
  }

  protected void onFail(final Throwable e) {

  }

  protected void onSucceed() {}
}
