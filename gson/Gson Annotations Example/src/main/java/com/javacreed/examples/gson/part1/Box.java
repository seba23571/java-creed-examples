package com.javacreed.examples.gson.part1;

import com.google.gson.annotations.SerializedName;

public class Box {

  @SerializedName("w")
  private int width;

  @SerializedName("h")
  private int height;

  @SerializedName("d")
  private int depth;

  public int getDepth() {
    return depth;
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
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

}
