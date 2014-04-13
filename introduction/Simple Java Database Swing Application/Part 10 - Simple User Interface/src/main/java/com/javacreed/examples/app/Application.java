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
package com.javacreed.examples.app;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class Application extends JFrame {

  private static final long serialVersionUID = 1386297934574206918L;

  private JTextField idTextField;
  private JTextField nameTextField;
  private JTextArea contactsTextArea;

  private JList<Contact> contactsList;

  private Action refreshAction;
  private Action newAction;
  private Action saveAction;
  private Action deleteAction;

  public Application() {
    initActions();
    initMenu();
    initComponents();
  }

  private JComponent createEditor() {
    final JPanel panel = new JPanel(new GridBagLayout());

    // Id
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.anchor = GridBagConstraints.WEST;
    constraints.insets = new Insets(2, 2, 2, 2);
    panel.add(new JLabel("Id"), constraints);

    constraints = new GridBagConstraints();
    constraints.gridx = 1;
    constraints.weightx = 1;
    constraints.insets = new Insets(2, 2, 2, 2);
    constraints.fill = GridBagConstraints.BOTH;
    idTextField = new JTextField();
    idTextField.setEditable(false);
    panel.add(idTextField, constraints);

    // Name
    constraints = new GridBagConstraints();
    constraints.gridy = 1;
    constraints.anchor = GridBagConstraints.WEST;
    constraints.insets = new Insets(2, 2, 2, 2);
    panel.add(new JLabel("Name"), constraints);

    constraints = new GridBagConstraints();
    constraints.gridx = 1;
    constraints.gridy = 1;
    constraints.weightx = 1;
    constraints.insets = new Insets(2, 2, 2, 2);
    constraints.fill = GridBagConstraints.BOTH;
    nameTextField = new JTextField();
    panel.add(nameTextField, constraints);

    // Contacts
    constraints = new GridBagConstraints();
    constraints.gridy = 2;
    constraints.anchor = GridBagConstraints.NORTHWEST;
    constraints.insets = new Insets(2, 2, 2, 2);
    panel.add(new JLabel("Contacts"), constraints);

    constraints = new GridBagConstraints();
    constraints.gridx = 1;
    constraints.gridy = 2;
    constraints.weightx = 1;
    constraints.weighty = 1;
    constraints.insets = new Insets(2, 2, 2, 2);
    constraints.fill = GridBagConstraints.BOTH;
    contactsTextArea = new JTextArea();
    panel.add(new JScrollPane(contactsTextArea), constraints);

    return panel;
  }

  private JComponent createListPane() {
    contactsList = new JList<>();
    return new JScrollPane(contactsList);
  }

  private void createNew() {
  }

  private JToolBar createToolBar() {
    final JToolBar toolBar = new JToolBar();
    toolBar.add(refreshAction);
    toolBar.addSeparator();
    toolBar.add(newAction);
    toolBar.add(saveAction);
    toolBar.addSeparator();
    toolBar.add(deleteAction);

    return toolBar;
  }

  private void delete() {
    if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Delete?", "Delete", JOptionPane.YES_NO_OPTION)) {
      // Delete
    }
  }

  private void initActions() {
    refreshAction = new AbstractAction("Refresh", load("Refresh")) {
      private static final long serialVersionUID = 7573537222039055715L;

      @Override
      public void actionPerformed(final ActionEvent e) {
        refreshData();
      }
    };

    newAction = new AbstractAction("New", load("New")) {
      private static final long serialVersionUID = 39402394060879678L;

      @Override
      public void actionPerformed(final ActionEvent e) {
        createNew();
      }
    };

    saveAction = new AbstractAction("Save", load("Save")) {
      private static final long serialVersionUID = 3151744204386109789L;

      @Override
      public void actionPerformed(final ActionEvent e) {
        save();
      }
    };

    deleteAction = new AbstractAction("Delete", load("Delete")) {
      private static final long serialVersionUID = -3865627438398974682L;

      @Override
      public void actionPerformed(final ActionEvent e) {
        delete();
      }
    };
  }

  private void initComponents() {
    add(createToolBar(), BorderLayout.PAGE_END);
    add(createListPane(), BorderLayout.WEST);
    add(createEditor(), BorderLayout.CENTER);
  }

  private void initMenu() {
    final JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);

    final JMenu editMenu = menuBar.add(new JMenu("Edit"));
    editMenu.add(refreshAction);
    editMenu.addSeparator();
    editMenu.add(newAction);
    editMenu.add(saveAction);
    editMenu.addSeparator();
    editMenu.add(deleteAction);
  }

  private ImageIcon load(final String name) {
    return new ImageIcon(getClass().getResource("/icons/" + name + ".png"));
  }

  private void refreshData() {
  }

  private void save() {
  }

}
