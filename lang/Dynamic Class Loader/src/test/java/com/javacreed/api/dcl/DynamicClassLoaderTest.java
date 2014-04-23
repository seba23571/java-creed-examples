package com.javacreed.api.dcl;

import java.lang.reflect.Method;

import org.junit.Test;

public class DynamicClassLoaderTest {

  @Test
  public void test() throws Exception {
    DynamicClassLoader dcl = new DynamicClassLoader(getClass().getClassLoader());
    dcl.loadJar(getClass().getResourceAsStream("/samples/collections-performance-0.0.1-SNAPSHOT.jar"));
    Class<?> main = dcl.loadClass("com.javacreed.examples.collections.Main");
    Method mainMethod = main.getMethod("main", String[].class);
    mainMethod.invoke(null, new Object[] { new String[] {} });
  }
}
