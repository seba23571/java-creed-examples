package com.javacreed.maven.examples;

import javax.swing.SwingUtilities;

public class Main {

  public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        CalculatorFrame app = new CalculatorFrame();
        app.setTitle("Maven Examples");
        app.pack();
        app.setDefaultCloseOperation(CalculatorFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
      }
    });
  }
}
