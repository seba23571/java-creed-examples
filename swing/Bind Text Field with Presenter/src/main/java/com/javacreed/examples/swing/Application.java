/*
 * #%L
 * Bind Text Field with Presenter
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
package com.javacreed.examples.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Application extends JFrame implements View {

  private static final long serialVersionUID = 5898379450269153800L;

  private final Presenter presenter;

  public Application(final Presenter presenter) {
    this.presenter = Objects.requireNonNull(presenter);
  }

  @Override
  public void init() {
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(final WindowEvent e) {
        presenter.onClosing();
      }
    });

    initComponents();
    pack();
  }

  private void initComponents() {
    setLayout(new GridBagLayout());

    GridBagConstraints constraints;

    constraints = new GridBagConstraints();
    constraints.insets = new Insets(10, 10, 10, 10);
    add(new JLabel("Name"), constraints);

    constraints = new GridBagConstraints();
    constraints.insets = new Insets(10, 10, 10, 10);
    constraints.weightx = 1;
    constraints.fill = GridBagConstraints.HORIZONTAL;
    add(presenter.getNameFieldBinder().attach(new JTextField(30)), constraints);
  }

  @Override
  public void showMessage(final String title, final String message) {
    JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
  }

}
