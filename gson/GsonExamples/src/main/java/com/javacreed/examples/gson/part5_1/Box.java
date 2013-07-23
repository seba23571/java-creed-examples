package com.javacreed.examples.gson.part5_1;

import com.google.gson.annotations.SerializedName;

public class Box {

  @SerializedName("w")
  private int width;

  @SerializedName("h")
  private int height;

  @SerializedName("d")
  private int depth;

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Box other = (Box) obj;
    if (depth != other.depth) {
      return false;
    }
    if (height != other.height) {
      return false;
    }
    if (width != other.width) {
      return false;
    }
    return true;
  }

  public int getDepth() {
    return depth;
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + depth;
    result = prime * result + height;
    result = prime * result + width;
    return result;
  }

  public void setDepth(final int depth) {
    this.depth = depth;
  }

  public void setHeight(final int height) {
    this.height = height;
  }

  public void setWidth(final int width) {
    this.width = width;
  }

  @Override
  public String toString() {
    return String.format("%d X %d X %d", width, height, depth);
  }
}
