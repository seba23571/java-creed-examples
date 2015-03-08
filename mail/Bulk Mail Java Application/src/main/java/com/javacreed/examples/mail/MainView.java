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
package com.javacreed.examples.mail;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Objects;

import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.TableColumn;
import javax.swing.text.html.HTMLEditorKit;

import com.javacreed.examples.mail.sendtest.SendTestDialog;
import com.javacreed.examples.mail.sendtest.SendTestView;

public class MainView extends JFrame implements View {

  private static final long serialVersionUID = 4301781707259296429L;

  private Presenter presenter;
  private JLabel statusLabel;
  private JPanel mainPanel;
  private JPanel actionsPanel;

  private JEditorPane messagePreviewArea;
  private JTextField subjectTextField;
  private JTextArea messageEditorArea;
  private JTextArea logsTextArea;

  private JComponent createDataComponent() {

    // Variables Table
    final JTable variablesTable = new JTable(presenter.getVariablesTableModel());
    variablesTable.setRowHeight(18);
    final TableColumn headersColumn = variablesTable.getColumnModel().getColumn(1);
    headersColumn.setCellEditor(new DefaultCellEditor(new JComboBox<>(presenter.getHeadersComboBoxModel())));
    // headersColumn.setCellRenderer(new ComboBoxTableCellRenderer(presenter.getHeadersComboBoxModel()));

    // Data Table
    final JTable dataTable = new JTable(presenter.getDataTableModel());
    dataTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(final ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
          final DefaultListSelectionModel model = (DefaultListSelectionModel) e.getSource();
          presenter.onDataValueChanged(model.getMinSelectionIndex());
        }
      }
    });

    // Preview
    messagePreviewArea = new JEditorPane();
    final HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
    messagePreviewArea.setEditorKit(htmlEditorKit);
    messagePreviewArea.setMargin(new Insets(2, 2, 2, 2));
    messagePreviewArea.setEditable(false);
    messagePreviewArea.setOpaque(false);

    final JSplitPane centerSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    centerSplitPane.setTopComponent(new JScrollPane(dataTable));
    centerSplitPane.setBottomComponent(new JScrollPane(messagePreviewArea));

    final JSplitPane mainSplitPane = new JSplitPane();
    mainSplitPane.setLeftComponent(new JScrollPane(variablesTable));
    mainSplitPane.setRightComponent(centerSplitPane);
    return mainSplitPane;
  }

  private JComponent createMessageComponent() {

    final JPanel panel = new JPanel(new GridBagLayout());

    GridBagConstraints constraints;

    constraints = new GridBagConstraints();
    constraints.insets = new Insets(10, 0, 5, 5);
    panel.add(new JLabel("Subject"), constraints);

    subjectTextField = new JTextField();
    subjectTextField.setMargin(new Insets(2, 2, 2, 2));
    subjectTextField.setFont(subjectTextField.getFont().deriveFont(14F));
    constraints = new GridBagConstraints();
    constraints.insets = new Insets(10, 5, 5, 0);
    constraints.weightx = 1;
    constraints.fill = GridBagConstraints.BOTH;
    panel.add(subjectTextField, constraints);

    // JToolBar toolBar = new JToolBar();
    // panel.add(toolBar, BorderLayout.NORTH);
    // toolBar.add(SwingUtils.addIcon(new StyledEditorKit.BoldAction(), "Bold"));
    // toolBar.add(SwingUtils.addIcon(new StyledEditorKit.ItalicAction(), "Italics"));
    // toolBar.add(SwingUtils.addIcon(new StyledEditorKit.UnderlineAction(), "Underline"));
    // toolBar.addSeparator();

    constraints = new GridBagConstraints();
    constraints.insets = new Insets(0, 0, 2, 0);
    constraints.gridy = 1;
    constraints.gridwidth = 2;
    constraints.anchor = GridBagConstraints.WEST;
    panel.add(new JLabel("Message"), constraints);

    messageEditorArea = new JTextArea();
    messageEditorArea.setFont(subjectTextField.getFont().deriveFont(14F));
    messageEditorArea.setMargin(new Insets(2, 2, 2, 2));
    messageEditorArea.setLineWrap(true);
    messageEditorArea.setWrapStyleWord(true);
    constraints = new GridBagConstraints();
    constraints.insets = new Insets(2, 0, 10, 0);
    constraints.gridy = 2;
    constraints.gridwidth = 2;
    constraints.weightx = 1;
    constraints.weighty = 1;
    constraints.fill = GridBagConstraints.BOTH;
    panel.add(new JScrollPane(messageEditorArea), constraints);

    return panel;
  }

  private JComponent createSendEmailComponent() {
    final JPanel panel = new JPanel(new BorderLayout(2, 2));

    logsTextArea = new JTextArea();
    logsTextArea.setEditable(false);

    panel.add(BorderLayout.CENTER, new JScrollPane(logsTextArea));

    return panel;
  }

  @Override
  public void destroy() {
    dispose();
  }

  @Override
  public String getEditorMessage() {
    return messageEditorArea.getText();
  }

  @Override
  public SendTestView getSendTestView() {
    return new SendTestDialog(this);
  }

  @Override
  public String getSubject() {
    return subjectTextField.getText();
  }

  @Override
  public void init() {
    initComponents();

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(final WindowEvent e) {
        presenter.onClosing();
      }
    });
    setIconImage(SwingUtils.getImage("Application"));
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    setTitle("Bulk Mail Java Application");
    setSize(800, 600);
    setExtendedState(Frame.MAXIMIZED_BOTH);
  }

  private void initComponents() {
    setLayout(new GridBagLayout());
    GridBagConstraints constraints;

    mainPanel = new JPanel(new CardLayout());
    mainPanel.add(createMessageComponent(), ViewPane.MESSAGE_PANE.name());
    mainPanel.add(createDataComponent(), ViewPane.DATA_PANE.name());
    mainPanel.add(createSendEmailComponent(), ViewPane.SEND_EMAIL_PANE.name());
    constraints = new GridBagConstraints();
    constraints.insets = new Insets(10, 10, 2, 10);
    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.gridwidth = 2;
    constraints.weightx = 1;
    constraints.weighty = 1;
    constraints.fill = GridBagConstraints.BOTH;
    add(mainPanel, constraints);

    statusLabel = new JLabel();
    constraints = new GridBagConstraints();
    constraints.insets = new Insets(2, 10, 10, 10);
    constraints.gridx = 0;
    constraints.gridy = 1;
    constraints.weightx = 1;
    constraints.anchor = GridBagConstraints.WEST;
    add(statusLabel, constraints);

    actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 2, 2));
    constraints = new GridBagConstraints();
    constraints.insets = new Insets(2, 2, 10, 10);
    constraints.gridx = 1;
    constraints.gridy = 1;
    constraints.anchor = GridBagConstraints.EAST;
    add(actionsPanel, constraints);
  }

  @Override
  public void setEditorMessage(final String message) {
    messageEditorArea.setText(message);
  }

  @Override
  public void setPresenter(final Presenter presenter) {
    this.presenter = Objects.requireNonNull(presenter);
  }

  @Override
  public void setPreviewMessage(final String message) {
    messagePreviewArea.setText(message);
  }

  @Override
  public void setStatus(final String status) {
    statusLabel.setText(status);
  }

  @Override
  public void setSubject(final String subject) {
    subjectTextField.setText(subject);
  }

  @Override
  public void showError(final String title, final String message) {
    JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void showMessage(final String title, final String message) {
    JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public File showNewDialog(final File currentDirectory, final File currentFile, final FileFilter fileFilter) {
    final JFileChooser fileChooser = new JFileChooser(currentDirectory);
    fileChooser.setSelectedFile(currentFile);
    fileChooser.setFileFilter(fileFilter);
    fileChooser.setApproveButtonText("New");
    if (fileChooser.showDialog(this, "New") == JFileChooser.APPROVE_OPTION) {
      return fileChooser.getSelectedFile();
    }
    return null;
  }

  @Override
  public File showOpenDialog(final File currentDirectory, final File currentFile, final FileFilter fileFilter) {
    final JFileChooser fileChooser = new JFileChooser(currentDirectory);
    fileChooser.setSelectedFile(currentFile);
    fileChooser.setFileFilter(fileFilter);
    if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
      return fileChooser.getSelectedFile();
    }
    return null;
  }

  @Override
  public void showPane(final ViewPane messagePane, final Action... actions) {
    final CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
    cardLayout.show(mainPanel, messagePane.name());

    actionsPanel.removeAll();
    for (final Action action : actions) {
      actionsPanel.add(new JButton(action));
    }
    revalidate();
  }

  @Override
  public File showSaveDialog(final File currentDirectory, final File currentFile, final FileFilter fileFilter) {
    final JFileChooser fileChooser = new JFileChooser(currentDirectory);
    fileChooser.setSelectedFile(currentFile);
    fileChooser.setFileFilter(fileFilter);
    if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
      return fileChooser.getSelectedFile();
    }
    return null;
  }

  @Override
  public void showView(final boolean show) {
    setVisible(show);
  }

  @Override
  public void showWarn(final String title, final String message) {
    JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
  }

}
