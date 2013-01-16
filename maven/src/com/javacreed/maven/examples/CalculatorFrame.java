package com.javacreed.maven.examples;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class CalculatorFrame extends JFrame {

  private final Calculator calculator = new CalculatorImpl();

  public CalculatorFrame() {
    initComponents();
  }

  private void initComponents() {
    setLayout(new FlowLayout());

    final JTextField inputA = new JTextField("0.00");
    inputA.setColumns(5);
    
    final JTextField inputB = new JTextField("0.00");
    inputB.setColumns(5);
    
    final JLabel result = new JLabel("0.00");

    final JButton calculate = new JButton("Calculate");
    calculate.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent event) {
        try {
          final double a = Double.parseDouble(inputA.getText());
          final double b = Double.parseDouble(inputB.getText());

          final double sum = calculator.sum(a, b);
          result.setText(String.format("%.2f", sum));
        } catch (final NumberFormatException e) {
          result.setText("Wrong input");
        }
      }
    });

    // Add the components to the frame
    add(new JLabel("Calculator:"));
    add(inputA);
    add(inputB);
    add(calculate);
    add(result);
  }
}
