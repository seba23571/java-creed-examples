package com.javacreed.examples.deadlock.part6;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Example2 {

  public static void main(final String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        final String message = "Always launch GUI from event dispatcher thread";

        final JLabel label = new JLabel(message);
        label.setFont(new Font("Arial", Font.BOLD, 22));

        final JFrame app = new JFrame();
        app.setTitle(message);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.add(label);
        app.pack();
        app.setLocationRelativeTo(null);
        app.setResizable(false);
        app.setVisible(true);
      }
    });
  }
}
