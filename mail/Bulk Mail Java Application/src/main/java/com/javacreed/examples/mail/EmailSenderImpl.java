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
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailSenderImpl implements EmailSender {

  private static final Logger LOGGER = LoggerFactory.getLogger(EmailSenderImpl.class);

  private final Properties properties;

  public EmailSenderImpl() throws IOException {
    final MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
    mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
    mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
    mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
    mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
    mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
    CommandMap.setDefaultCommandMap(mc);

    properties = new Properties();
    properties.load(EmailSenderImpl.class.getResourceAsStream("/mail.yahoo.properties"));
  }

  @Override
  public void send(final Email email) throws UnsupportedEncodingException, MessagingException {
    EmailSenderImpl.LOGGER.debug("Sending email: {}", email);
    final Session session = Session.getInstance(properties);
    session.setDebug(true);

    final Multipart multipart = new MimeMultipart();

    EmailSenderImpl.LOGGER.debug("  >> Adding body");
    final MimeBodyPart body = new MimeBodyPart();
    body.setContent(email.getMessage(), "text/html");
    multipart.addBodyPart(body);

    EmailSenderImpl.LOGGER.debug("  >> Adding embedded images");
    for (final Map.Entry<String, DataSource> entry : email.getEmbedded().entrySet()) {
      EmailSenderImpl.LOGGER.debug("    >> {}", entry.getKey());
      final MimeBodyPart part = new MimeBodyPart();
      part.setDataHandler(new DataHandler(entry.getValue()));
      part.addHeader("Content-ID", "<" + entry.getKey() + ">");
      multipart.addBodyPart(part);
    }

    EmailSenderImpl.LOGGER.debug("  >> Adding Attachments");
    for (final DataSource attachment : email.getAttachments()) {
      final MimeBodyPart part = new MimeBodyPart();
      part.setDataHandler(new DataHandler(attachment));
      multipart.addBodyPart(part);
    }

    EmailSenderImpl.LOGGER.debug("  >> Creating message");
    final Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress(properties.getProperty("mail.smtp.username"), properties
        .getProperty("mail.smtp.name")));
    message.addRecipient(Message.RecipientType.TO, email.getRecipientAddress());
    message.setSubject(email.getSubject());
    message.setContent(multipart);

    EmailSenderImpl.LOGGER.debug("  >> Sending email");
    final Transport transport = session.getTransport("smtp");
    transport.connect(properties.getProperty("mail.smtp.host"),
        Integer.parseInt(properties.getProperty("mail.smtp.port")), properties.getProperty("mail.smtp.username"),
        properties.getProperty("mail.smtp.password"));
    transport.sendMessage(message, message.getAllRecipients());

    EmailSenderImpl.LOGGER.debug("Done");
  }
}
