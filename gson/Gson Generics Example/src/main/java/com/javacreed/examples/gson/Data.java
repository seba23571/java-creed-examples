package com.javacreed.examples.gson;

public class Data<T> {

  private T data;
  private int version;

  public T getData() {
    return data;
  }

  public int getVersion() {
    return version;
  }

  public void setData(final T data) {
    this.data = data;
  }

  public void setVersion(final int version) {
    this.version = version;
  }

  @Override
  public String toString() {
    return "Data [data=" + data + ", version=" + version + "]";
  }

}
