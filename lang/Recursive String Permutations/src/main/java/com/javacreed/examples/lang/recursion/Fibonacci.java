package com.javacreed.examples.lang.recursion;

public class Fibonacci {

  public static long number(long index) {
    // Base Case: The first two Fibonacci numbers are 1 and 1
    if (index < 2) {
      return 1;
    }

    // F(n) = F(n-1) + F(n-2)
    return number(index - 1) + number(index - 2);
  }

  public static void main(String[] args) {
    long fib = number(5);
    System.out.println(fib);
  }
}
