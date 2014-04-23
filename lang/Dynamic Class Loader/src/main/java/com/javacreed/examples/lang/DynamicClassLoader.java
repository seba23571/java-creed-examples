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
package com.javacreed.examples.lang;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicClassLoader extends ClassLoader {

  private static final Logger LOGGER = LoggerFactory.getLogger(DynamicClassLoader.class);

  private final ConcurrentMap<String, byte[]> loadedClasses = new ConcurrentHashMap<>();

  public DynamicClassLoader() {
  }

  public DynamicClassLoader(final ClassLoader parent) {
    super(parent);
  }

  @Override
  protected Class<?> findClass(final String name) throws ClassNotFoundException {
    DynamicClassLoader.LOGGER.debug("Finding class: {}", name);
    if (loadedClasses.containsKey(name)) {
      final byte[] b = loadedClasses.get(name);
      return defineClass(name, b, 0, b.length);
    }

    return super.findClass(name);
  }

  public void loadJar(final InputStream in) throws IOException {
    try (BufferedInputStream bis = new BufferedInputStream(in); ZipInputStream zis = new ZipInputStream(bis)) {
      for (ZipEntry ze = zis.getNextEntry(); ze != null; ze = zis.getNextEntry()) {
        if (ze.isDirectory()) {
          continue;
        }

        final String name = ze.getName();
        final String canonicalName = StringUtils.removeEnd(name, ".class").replaceAll("[\\/]", ".");
        final byte[] classBytes = IOUtils.toByteArray(zis);
        if (loadedClasses.putIfAbsent(canonicalName, classBytes) == null) {
          DynamicClassLoader.LOGGER.debug("Loading class: {} of {} bytes", canonicalName, classBytes.length);
        } else {
          DynamicClassLoader.LOGGER.debug("Skipping class: {} of {} bytes as onle already exists", canonicalName,
              classBytes.length);
        }
      }
    }
  }
}
