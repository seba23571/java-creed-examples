package com.javacreed.examples.maven.wum;
import javax.swing.SwingUtilities;

public class Main {

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        Application app = new Application();
        app.setTitle("Simple Application");
        app.setDefaultCloseOperation(Application.EXIT_ON_CLOSE);
        app.setSize(600, 300);
        app.setVisible(true);
      }
    });
  }
}
