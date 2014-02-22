package com.javacreed.examples.swing.worker.part3;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker.StateValue;

public class Application extends JFrame {

  /**  */
  private static final long serialVersionUID = -8668818312732181049L;

  private Action searchCancelAction;
  private Action browseAction;

  private JTextField wordTextField;
  private JTextField directoryPathTextField;
  private JTextArea messagesTextArea;
  private JProgressBar searchProgressBar;

  private SearchForWordWorker searchWorker;

  public Application() {
    initActions();
    initComponents();
  }

  private void cancel() {
    searchWorker.cancel(true);
  }

  private void initActions() {
    browseAction = new AbstractAction("Browse") {

      private static final long serialVersionUID = 4669650683189592364L;

      @Override
      public void actionPerformed(final ActionEvent e) {
        final File dir = new File(directoryPathTextField.getText()).getAbsoluteFile();
        final JFileChooser fileChooser = new JFileChooser(dir.getParentFile());
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        final int option = fileChooser.showOpenDialog(Application.this);
        if (option == JFileChooser.APPROVE_OPTION) {
          final File selected = fileChooser.getSelectedFile();
          directoryPathTextField.setText(selected.getAbsolutePath());
        }
      }
    };

    searchCancelAction = new AbstractAction("Search") {

      private static final long serialVersionUID = 4669650683189592364L;

      @Override
      public void actionPerformed(final ActionEvent e) {
        if (searchWorker == null) {
          search();
        } else {
          cancel();
        }
      }
    };
  }

  private void initComponents() {
    setLayout(new GridBagLayout());

    GridBagConstraints constraints = new GridBagConstraints();
    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.insets = new Insets(2, 2, 2, 2);
    add(new JLabel("Word: "), constraints);

    wordTextField = new JTextField();
    wordTextField.setText("Hello");
    constraints = new GridBagConstraints();
    constraints.gridx = 1;
    constraints.gridy = 0;
    constraints.gridwidth = 2;
    constraints.insets = new Insets(2, 2, 2, 2);
    constraints.weightx = 1;
    constraints.fill = GridBagConstraints.BOTH;
    add(wordTextField, constraints);

    constraints = new GridBagConstraints();
    constraints.gridx = 0;
    constraints.gridy = 1;
    constraints.insets = new Insets(2, 2, 2, 2);
    add(new JLabel("Path: "), constraints);

    directoryPathTextField = new JTextField();
    directoryPathTextField.setText("C:\\Users\\Albert\\Work\\JavaCreed\\examples");
    constraints = new GridBagConstraints();
    constraints.gridx = 1;
    constraints.gridy = 1;
    constraints.gridwidth = 1;
    constraints.insets = new Insets(2, 2, 2, 2);
    constraints.weightx = 1;
    constraints.fill = GridBagConstraints.BOTH;
    add(directoryPathTextField, constraints);

    constraints = new GridBagConstraints();
    constraints.gridx = 2;
    constraints.gridy = 1;
    constraints.insets = new Insets(2, 2, 2, 2);
    add(new JButton(browseAction), constraints);

    messagesTextArea = new JTextArea();
    messagesTextArea.setEditable(false);
    constraints = new GridBagConstraints();
    constraints.gridx = 0;
    constraints.gridy = 2;
    constraints.gridwidth = 3;
    constraints.insets = new Insets(2, 2, 2, 2);
    constraints.weightx = 1;
    constraints.weighty = 1;
    constraints.fill = GridBagConstraints.BOTH;
    add(new JScrollPane(messagesTextArea), constraints);

    searchProgressBar = new JProgressBar();
    searchProgressBar.setStringPainted(true);
    searchProgressBar.setVisible(false);
    constraints = new GridBagConstraints();
    constraints.gridx = 0;
    constraints.gridy = 3;
    constraints.gridwidth = 2;
    constraints.insets = new Insets(2, 2, 2, 2);
    constraints.weightx = 1;
    constraints.fill = GridBagConstraints.BOTH;
    add(searchProgressBar, constraints);

    constraints = new GridBagConstraints();
    constraints.gridx = 2;
    constraints.gridy = 3;
    constraints.insets = new Insets(2, 2, 2, 2);
    constraints.weightx = 0;
    add(new JButton(searchCancelAction), constraints);
  }

  private void search() {
    final String word = wordTextField.getText();
    final File directory = new File(directoryPathTextField.getText());
    messagesTextArea.setText("Searching for word '" + word + "' in text files under: " + directory.getAbsolutePath()
        + "\n");
    searchWorker = new SearchForWordWorker(word, directory, messagesTextArea);
    searchWorker.addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(final PropertyChangeEvent event) {
        switch (event.getPropertyName()) {
        case "progress":
          searchProgressBar.setIndeterminate(false);
          searchProgressBar.setValue((Integer) event.getNewValue());
          break;
        case "state":
          switch ((StateValue) event.getNewValue()) {
          case DONE:
            searchProgressBar.setVisible(false);
            searchCancelAction.putValue(Action.NAME, "Search");
            searchWorker = null;
            break;
          case STARTED:
          case PENDING:
            searchCancelAction.putValue(Action.NAME, "Cancel");
            searchProgressBar.setVisible(true);
            searchProgressBar.setIndeterminate(true);
            break;
          }
          break;
        }
      }
    });
    searchWorker.execute();
  }
}
