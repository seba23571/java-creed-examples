package com.javacreed.examples.maven.um;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

  public static void main(final String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        final Application app = new Application();
        app.setTitle("Simple Application");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setSize(600, 300);
        app.setVisible(true);
      }
    });
  }
}
