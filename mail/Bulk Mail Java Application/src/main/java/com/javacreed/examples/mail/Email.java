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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.activation.DataSource;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.util.ByteArrayDataSource;

public class Email {

  public static class Builder {
    private final Map<String, DataSource> embedded = new LinkedHashMap<>();
    private final List<DataSource> attachments = new LinkedList<>();
    private String recipient;
    private String subject;
    private String message;

    public Builder addAttachment(final DataSource dataSource) {
      attachments.add(dataSource);
      return this;
    }

    public Builder addAttachment(final String attachmentType, final InputStream is) throws IOException {
      return addAttachment(new ByteArrayDataSource(is, attachmentType));
    }

    public void addAttachments(final Collection<DataSource> attachments) {
      this.attachments.addAll(attachments);
    }

    public void addEmbedded(final Map<String, DataSource> embedded) {
      this.embedded.putAll(embedded);
    }

    public Builder addEmbedded(final String name, final DataSource dataSource) {
      embedded.put(name, dataSource);
      return this;
    }

    public Builder addEmbeddedImage(final String name, final String imageType, final InputStream is) throws IOException {
      return addEmbedded(name, new ByteArrayDataSource(is, imageType));
    }

    public Email build() {
      return new Email(this);
    }

    public Builder setMessage(final String message) {
      this.message = message;
      return this;
    }

    public Builder setRecipient(final String recipient) {
      this.recipient = recipient;
      return this;
    }

    public Builder setSubject(final String subject) {
      this.subject = subject;
      return this;
    }
  }

  // TODO: consider using a list of recipients
  private final String recipient;
  private final String subject;
  private final String message;
  private final Map<String, DataSource> embedded;

  private final List<DataSource> attachments;

  private Email(final Builder builder) {
    this(builder.recipient, builder.subject, builder.message, builder.embedded, builder.attachments);
  }

  public Email(final String recipient, final String subject, final String message) {
    this(recipient, subject, message, Collections.emptyMap(), Collections.emptyList());
  }

  public Email(final String recipient, final String subject, final String message,
      final Map<String, DataSource> embedded, final List<DataSource> attachments) {
    this.recipient = Objects.requireNonNull(recipient);
    this.subject = Objects.requireNonNull(subject);
    this.message = Objects.requireNonNull(message);
    this.embedded = Collections.unmodifiableMap(new LinkedHashMap<>(embedded));
    this.attachments = Collections.unmodifiableList(new ArrayList<>(attachments));
  }

  public List<DataSource> getAttachments() {
    return attachments;
  }

  public Map<String, DataSource> getEmbedded() {
    return embedded;
  }

  public String getMessage() {
    return message;
  }

  public String getRecipient() {
    return recipient;
  }

  public InternetAddress getRecipientAddress() throws AddressException {
    return new InternetAddress(recipient);
  }

  public String getSubject() {
    return subject;
  }

  @Override
  public String toString() {
    return recipient;
  }
}
