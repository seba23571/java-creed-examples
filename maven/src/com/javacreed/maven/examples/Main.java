package com.javacreed.maven.examples;

import javax.swing.SwingUtilities;

import com.javacreed.examples.x.PrintUtil;

public class Main {

  public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        PrintUtil.print("Starting application");
        CalculatorFrame app = new CalculatorFrame();
        app.setTitle("Maven Examples");
        app.pack();
        app.setDefaultCloseOperation(CalculatorFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
      }
    });
  }
}
