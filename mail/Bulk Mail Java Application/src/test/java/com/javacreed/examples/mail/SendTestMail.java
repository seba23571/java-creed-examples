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

import org.junit.Test;

public class SendTestMail {

  @Test
  public void test() throws Exception {
    final Email.Builder builder = new Email.Builder();
    builder.setRecipient("albertattard@gmail.com");
    builder.setSubject("Test email with image");
    builder.setMessage("<H1>Photos of Valletta</H1><br/> <p align=center><img src=\"cid:image1\"> </p>"
        + "<p align=center><img src=\"cid:image2\"> </p>" + "<p align=center><img src=\"cid:image3\"> </p>");

    builder.addEmbeddedImage("image1", "image/jpg", getClass().getResourceAsStream("/embedded/image1.jpg"));
    builder.addEmbeddedImage("image2", "image/jpg", getClass().getResourceAsStream("/embedded/image2.jpg"));
    builder.addEmbeddedImage("image3", "image/jpg", getClass().getResourceAsStream("/embedded/image3.jpg"));

    builder.addAttachment("application/pdf", getClass().getResourceAsStream("/attachments/attachment1.pdf"));

    final EmailSender emailSender = new EmailSenderImpl();
    emailSender.send(builder.build());
  }
}
