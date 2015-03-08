/*
 * #%L
 * Bulk Mail Java Application
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2012 - 2015 Java Creed
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.javacreed.examples.mail.sendtest;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class SendTestDialog extends JDialog implements SendTestView {

  private SendTestPresenter presenter;
  private final Map<String, JTextField> variablesTextFields = new HashMap<>();
  private JLabel statusLabel;

  public SendTestDialog(final Window window) {
    super(window);
  }

  @Override
  public void destroy() {
    dispose();
  }

  @Override
  public String getValueFor(final String key) {
    return variablesTextFields.get(key).getText();
  }

  @Override
  public void init() {
    setTitle("Send Test Email");
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(final WindowEvent e) {
        presenter.onClosing();
      }
    });

    initComponents();
    pack();
    setLocationRelativeTo(getOwner());
  }

  private void initComponents() {
    setLayout(new GridBagLayout());

    GridBagConstraints constraints;

    final Set<String> variables = presenter.getVariablesNames();
    int row = 0;
    for (final String variable : variables) {
      constraints = new GridBagConstraints();
      constraints.insets = new Insets(10, 10, 10, 10);
      constraints.gridx = 0;
      constraints.gridy = row;
      constraints.anchor = GridBagConstraints.WEST;
      add(new JLabel(variable), constraints);

      final JTextField textField = new JTextField();
      textField.setFont(textField.getFont().deriveFont(14F));
      constraints = new GridBagConstraints();
      constraints.insets = new Insets(10, 10, 10, 10);
      constraints.gridx = 1;
      constraints.gridy = row++;
      constraints.gridwidth = 3;
      constraints.weightx = 1;
      constraints.fill = GridBagConstraints.HORIZONTAL;
      add(textField, constraints);

      this.variablesTextFields.put(variable, textField);
    }

    constraints = new GridBagConstraints();
    constraints.insets = new Insets(10, 10, 10, 10);
    constraints.gridx = 0;
    constraints.gridy = row;
    constraints.gridwidth = 2;
    constraints.weightx = 1;
    constraints.fill = GridBagConstraints.HORIZONTAL;
    add(statusLabel = new JLabel(), constraints);

    constraints = new GridBagConstraints();
    constraints.insets = new Insets(10, 10, 10, 2);
    constraints.gridx = 2;
    constraints.gridy = row;
    add(new JButton(presenter.getCancelAction()), constraints);

    constraints = new GridBagConstraints();
    constraints.insets = new Insets(10, 2, 10, 10);
    constraints.gridx = 3;
    constraints.gridy = row;
    add(new JButton(presenter.getSendAction()), constraints);
  }

  @Override
  public void setPresenter(final SendTestPresenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void showMessage(final String title, final String message) {
    JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void showView(final boolean b) {
    setVisible(b);
  }

  @Override
  public void showWarn(final String title, final String message) {
    JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
  }

}
