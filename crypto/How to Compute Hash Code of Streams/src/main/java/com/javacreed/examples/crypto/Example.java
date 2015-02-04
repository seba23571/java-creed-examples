/*
 * #%L
 * How to Compute Hash Code of Streams
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
package com.javacreed.examples.crypto;

import java.io.BufferedInputStream;
import java.net.URL;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Formatter;

public class Example {
  public static void main(final String[] args) throws Exception {

    final URL link = new URL("http://www.javacreed.com/");
    try (DigestInputStream in = new DigestInputStream(new BufferedInputStream(link.openStream()),
        MessageDigest.getInstance("SHA-1"))) {
      // Read the stream and do nothing with it
      while (in.read() != -1) {}

      // Get the digest and finialise the computation
      final MessageDigest md = in.getMessageDigest();
      final byte[] digest = md.digest();

      // Format as HEX
      try (Formatter formatter = new Formatter()) {
        for (final byte b : digest) {
          formatter.format("%02x", b);
        }

        final String sha1 = formatter.toString();
        System.out.println(sha1);
      }
    }
  }
}
