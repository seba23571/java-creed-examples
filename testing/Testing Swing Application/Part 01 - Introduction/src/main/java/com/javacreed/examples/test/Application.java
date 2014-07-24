package com.javacreed.examples.test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

public class Application extends JFrame {

  private static final long serialVersionUID = -7343389333426996183L;

  private Action openAction;

  public Application() {
    initActions();
    initMenu();
    initToolbar();
  }

  private void initActions() {
    openAction = new AbstractAction("Open", new ImageIcon(Application.class.getResource("/icons/open.png"))) {
      private static final long serialVersionUID = -8733978494447301385L;

      @Override
      public void actionPerformed(final ActionEvent e) {
        showOpenFileChooser();
      }
    };
  }

  private void initMenu() {
    final JMenuBar menubar = new JMenuBar();
    setJMenuBar(menubar);

    final JMenu file = menubar.add(new JMenu("File"));
    file.add(openAction);
  }

  private void initToolbar() {
    final JToolBar toolBar = new JToolBar();
    add(toolBar, BorderLayout.PAGE_START);

    toolBar.add(openAction);
  }

  public void showOpenFileChooser() {
    final JFileChooser fileChooser = new JFileChooser(new File("C:\\Users\\Albert\\JavaCreed\\code\\testing-swing-application\\samples"));
    if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
      final File file = fileChooser.getSelectedFile();
      if (file != null) {
        if (file.isFile()) {
          // Load the file
          System.out.println("Loading...");
        } else {
          JOptionPane.showMessageDialog(this, "The file: '" + file.getAbsolutePath() + "' is not a file", "Open File",
              JOptionPane.WARNING_MESSAGE);
        }
      }
    }
  }

}
