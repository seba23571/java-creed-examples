package com.javacreed.examples.lang.recursion;

public class DifferentStringPermutation {

  private static void doPermute(final char[] in, final StringBuffer outputString, 
      final boolean[] used, final int inputLength, final int level) {
    if (level == inputLength) {
      System.out.println(outputString.toString());
      return;
    }

    for (int i = 0; i < inputLength; ++i) {
      if (used[i]) {
        continue;
      }
      outputString.append(in[i]);
      used[i] = true;
      DifferentStringPermutation.doPermute(in, outputString, used, in.length, level + 1);
      used[i] = false;
      outputString.setLength(outputString.length() - 1);
    }
  }

  public static void main(final String[] args) {
    final String input = "AB";
    final int inputLength = input.length();
    final boolean[] used = new boolean[inputLength];
    final StringBuffer outputString = new StringBuffer();
    final char[] in = input.toCharArray();

    DifferentStringPermutation.doPermute(in, outputString, used, inputLength, 0);
  }
}
