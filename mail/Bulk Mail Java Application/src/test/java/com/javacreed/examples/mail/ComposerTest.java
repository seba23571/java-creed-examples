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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class ComposerTest {

  @Test
  public void testCreateEmail() {
    final Composer composer = new Composer();
    composer.setSubject("Test Emails");
    composer.setMessage("This is a basic message");

    final Map<String, String> map = composer.createParameters();
    map.put("email", "albertattard@gmail.com");
    final Email email = composer.createEmail(map);
    Assert.assertNotNull(email);
    Assert.assertEquals("albertattard@gmail.com", email.getRecipient());
  }

  @Test
  public void testSerialiser() throws Exception {
    final Composer composer = new Composer();
    composer.setSubject("Hello");
    composer.setMessage("This is a test message");
    composer.addAttachment("application/pdf", getClass().getResourceAsStream("/attachments/attachment1.pdf"));
    composer.addEmbedded("image1", "image/jpg", getClass().getResourceAsStream("/embedded/image1.jpg"));

    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    composer.saveToStream(baos);

    final Composer deserialised = Composer.readFromStream(new ByteArrayInputStream(baos.toByteArray()));
    Assert.assertNotNull(deserialised);
    Assert.assertEquals(composer.getSubject(), deserialised.getSubject());
    Assert.assertEquals(composer.getMessage(), deserialised.getMessage());
    Assert.assertEquals(composer.getEmbedded(), deserialised.getEmbedded());
    Assert.assertEquals(composer.getAttachments(), deserialised.getAttachments());
  }
}
