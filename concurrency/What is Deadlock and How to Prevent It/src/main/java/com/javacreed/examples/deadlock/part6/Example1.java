package com.javacreed.examples.deadlock.part6;

import javax.swing.SwingUtilities;

import com.javacreed.examples.deadlock.utils.ThreadUtils;

public class Example1 {

  public static void main(final String[] args) {
    ThreadUtils.log("Add a new event to the event queue");
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        ThreadUtils.log("Executed from the event thread");
      }
    });
  }
}
