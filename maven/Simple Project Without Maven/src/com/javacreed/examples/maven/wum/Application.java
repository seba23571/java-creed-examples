package com.javacreed.examples.maven.wum;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class Application extends JFrame {

  public Application() {
    initComponents();
  }

  private void addNumbers(final String text) {
    try {
      final Calculator calc = new Calculator();
      final double[] numbers = calc.parseNumbers(text);
      final double total = calc.add(numbers);
      JOptionPane.showMessageDialog(this, "The total is: " + total, "Add Numbers", JOptionPane.INFORMATION_MESSAGE);
    } catch (final NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Cannot parse text", "Add Numbers", JOptionPane.WARNING_MESSAGE);
    }
  }

  private void initComponents() {
    final JTextArea textArea = new JTextArea();
    textArea.setFont(new Font("Courier New", Font.PLAIN, 22));
    add(new JScrollPane(textArea), BorderLayout.CENTER);

    final JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    add(panel, BorderLayout.SOUTH);

    final JButton addButton = new JButton("Add");
    addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        addNumbers(textArea.getText());
      }
    });
    panel.add(addButton);
  }
}
