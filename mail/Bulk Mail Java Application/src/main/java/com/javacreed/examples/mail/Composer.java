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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

public class Composer implements Serializable {

  public static class BufferedData implements Data {
    private static final long serialVersionUID = -428001279432913362L;
    private final String type;
    private final byte[] data;

    public BufferedData(final String type, final byte[] data) {
      this.type = Objects.requireNonNull(type);
      this.data = Objects.requireNonNull(data);
    }

    public BufferedData(final String type, final InputStream in) throws IOException {
      this.type = Objects.requireNonNull(type);
      try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
        final byte[] buffer = new byte[1024];
        for (int length; (length = in.read(buffer)) > 0;) {
          out.write(buffer, 0, length);
        }

        this.data = out.toByteArray();
      }
    }

    @Override
    public DataSource createDataSource() {
      return new ByteArrayDataSource(data, type);
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof BufferedData)) {
        return false;
      }

      final BufferedData other = (BufferedData) o;

      if (!Arrays.equals(data, other.data)) {
        return false;
      }
      if (!type.equals(other.type)) {
        return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      int result = type.hashCode();
      result = 31 * result + Arrays.hashCode(data);
      return result;
    }
  }

  public static interface Data extends Serializable {
    DataSource createDataSource();
  }

  public static Composer readFromFile(final File file) throws IOException, ClassNotFoundException {
    try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
      return Composer.readFromObjectStream(ois);
    }
  }

  public static Composer readFromObjectStream(final ObjectInputStream in) throws IOException, ClassNotFoundException {
    return (Composer) in.readObject();
  }

  public static Composer readFromStream(final InputStream in) throws IOException, ClassNotFoundException {
    if (in instanceof ObjectInputStream) {
      return Composer.readFromObjectStream((ObjectInputStream) in);
    } else {
      return Composer.readFromObjectStream(new ObjectInputStream(in));
    }
  }

  private static final long serialVersionUID = 3658474559662480630L;

  private String subject = "";

  private Map<String, Data> embedded = new LinkedHashMap<>();

  private List<Data> attachments = new LinkedList<>();

  private transient MessageParser messageParser = new MessageParser("");

  public void addAttachment(final Data attachment) {
    this.attachments.add(attachment);
  }

  public void addAttachment(final String type, final InputStream in) throws IOException {
    addAttachment(new BufferedData(type, in));
  }

  public void addEmbedded(final String name, final Data dataSource) {
    this.embedded.put(name, dataSource);
  }

  public void addEmbedded(final String name, final String type, final InputStream in) throws IOException {
    addEmbedded(name, new BufferedData(type, in));
  }

  public Email createEmail(final Map<String, String> parameters) {
    final Email.Builder builder = new Email.Builder();
    builder.setRecipient(parameters.get("email"));
    builder.setSubject(replaceVariablesWithData(getSubject(), parameters));
    builder.setMessage(replaceVariablesWithData(getMessage(), parameters));
    for (final Map.Entry<String, Data> data : embedded.entrySet()) {
      builder.addEmbedded(data.getKey(), data.getValue().createDataSource());
    }
    for (final Data data : attachments) {
      builder.addAttachment(data.createDataSource());
    }
    return builder.build();
  }

  public Map<String, String> createParameters() {
    final Set<String> variablesNames = getVariablesNames();
    final Map<String, String> map = new HashMap<>(variablesNames.size());
    for (final String variableName : variablesNames) {
      map.put(variableName, variableName);
    }
    return map;
  }

  public List<Data> getAttachments() {
    return attachments;
  }

  public Map<String, Data> getEmbedded() {
    return embedded;
  }

  public String getMessage() {
    return messageParser.getMessage();
  }

  public String getSubject() {
    return subject;
  }

  public Set<String> getVariablesNames() {
    final Set<String> set = new LinkedHashSet<>();
    set.add("email");
    if (messageParser != null) {
      set.addAll(messageParser.getVariablesNames());
    }
    return set;
  }

  private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
    subject = (String) in.readObject();
    messageParser = new MessageParser((String) in.readObject());
    embedded = (Map<String, Data>) in.readObject();
    attachments = (List<Data>) in.readObject();
  }

  public String replaceVariablesWithData(String data, final Map<String, String> parameters) {
    // TODO: this is not cheap and need to see a better way
    for (final Map.Entry<String, String> binding : parameters.entrySet()) {
      if (data.contains("${" + binding.getKey() + "}")) {
        data = data.replaceAll("\\$\\{" + binding.getKey() + "\\}", binding.getValue());
      }
    }

    return data;
  }

  public void saveToFile(final File file) throws IOException {
    final File parent = file.getParentFile();
    if (parent != null) {
      parent.mkdirs();
    }
    try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
      saveToObjectStream(oos);
    }
  }

  public void saveToObjectStream(final ObjectOutputStream out) throws IOException {
    out.writeObject(this);
  }

  public void saveToStream(final OutputStream out) throws IOException {
    if (out instanceof ObjectOutputStream) {
      saveToObjectStream((ObjectOutputStream) out);
    } else {
      saveToObjectStream(new ObjectOutputStream(out));
    }
  }

  public void setMessage(final String message) {
    messageParser = new MessageParser(Objects.requireNonNull(message));
  }

  public void setSubject(final String subject) {
    this.subject = Objects.requireNonNull(subject);
  }

  private void writeObject(final ObjectOutputStream out) throws IOException {
    out.writeObject(subject);
    out.writeObject(messageParser.getMessage());
    out.writeObject(embedded);
    out.writeObject(attachments);
  }
}
