package com.javacreed.examples.maven.wum;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class Application extends JFrame {

  private JTextArea textArea;

  public Application() {
    initComponents();
  }

  private void initComponents() {
    textArea = new JTextArea();
    add(new JScrollPane(textArea), BorderLayout.CENTER);
    
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    add(panel, BorderLayout.SOUTH);
    
    JButton saveButton = new JButton("Save");
    saveButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
      }
    });
    panel.add(saveButton);
  }
}
