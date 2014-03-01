/**
 * Copyright 2012-2014 Java Creed.
 * 
 * Licensed under the Apache License, Version 2.0 (the "<em>License</em>");
 * you may not use this file except in compliance with the License. You may 
 * obtain a copy of the License at: 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 */
package com.javacreed.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Application extends JFrame {

  private static final long serialVersionUID = 5762213140150393157L;

  private JTextArea messages;
  private JProgressBar progressBar;
  private JButton run;
  private JButton cancel;

  public Application() {
    initComponents();
  }

  private void doCancel() {
    run.setEnabled(true);
    cancel.setEnabled(false);
  }

  private void doRun() {
    run.setEnabled(false);
    cancel.setEnabled(true);
    messages.setText("");
  }

  private void initComponents() {
    setLayout(new GridBagLayout());

    GridBagConstraints constraints = new GridBagConstraints();
    constraints.insets = new Insets(2, 2, 2, 2);
    constraints.gridwidth = 3;
    constraints.weightx = 1;
    constraints.weighty = 1;
    constraints.fill = GridBagConstraints.BOTH;
    messages = new JTextArea();
    add(new JScrollPane(messages), constraints);

    progressBar = new JProgressBar();
    constraints = new GridBagConstraints();
    constraints.insets = new Insets(2, 2, 2, 2);
    constraints.gridx = 0;
    constraints.gridy = 1;
    constraints.weightx = 1;
    constraints.fill = GridBagConstraints.BOTH;
    add(progressBar, constraints);

    run = new JButton("Run");
    run.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        doRun();
      }
    });
    constraints = new GridBagConstraints();
    constraints.insets = new Insets(2, 2, 2, 2);
    constraints.gridx = 1;
    constraints.gridy = 1;
    add(run, constraints);

    cancel = new JButton("Cancel");
    cancel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        doCancel();
      }
    });
    cancel.setEnabled(false);
    constraints = new GridBagConstraints();
    constraints.insets = new Insets(2, 2, 2, 2);
    constraints.gridx = 2;
    constraints.gridy = 1;
    add(cancel, constraints);
  }
}
