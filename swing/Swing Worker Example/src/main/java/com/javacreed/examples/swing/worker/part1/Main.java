package com.javacreed.examples.swing.worker.part1;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
  public static void main(final String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        final JFrame frame = new JFrame();
        frame.setTitle("Test Frame");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
      }
    });
  }
}
