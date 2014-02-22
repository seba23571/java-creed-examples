package com.javacreed.examples.swing.worker.part3;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
  public static void main(final String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        final Application frame = new Application();
        frame.setTitle("Swing Worker Demo");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
      }
    });
  }
}
