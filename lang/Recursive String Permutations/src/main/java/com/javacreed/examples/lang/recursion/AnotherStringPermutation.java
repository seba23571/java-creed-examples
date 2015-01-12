package com.javacreed.examples.lang.recursion;

public class AnotherStringPermutation {

  public static void main(final String[] args) {
    final AnotherStringPermutation aps = new AnotherStringPermutation("AB");
    aps.permute();
  }

  private final boolean[] used;
  private final StringBuilder out;
  private final String in;

  public AnotherStringPermutation(final String str) {
    used = new boolean[str.length()];
    out = new StringBuilder();
    in = str;
  }

  public void permute() {
    if (out.length() == in.length()) {
      System.out.println(out);
      return;
    }
    
    for (int i = 0; i < in.length(); i++) {
      if (used[i]) {
        continue;
      }
      out.append(in.charAt(i));
      used[i] = true;
      permute();
      used[i] = false;
      out.setLength(out.length() - 1);
    }
  }
}
