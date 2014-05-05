package com.javacreed.examples.swing.font.part1;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Application extends JFrame {

    private static final long serialVersionUID = -4536765196356760247L;

    public Application() {
        initComponents();
    }

    private void initComponents() {
        final JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        final JMenu fileMenu = menuBar.add(new JMenu("File"));
        fileMenu.add("Menu Entry");

        final List<JComponent> components = new ArrayList<>();
        components.add(new JLabel("Label"));
        components.add(new JButton("Button"));
        components.add(new JRadioButton("Radio Button"));
        components.add(new JTextField("Text Field"));
        components.add(new JScrollPane(new JTextArea("Text Area\nText Area\nText Area\nText Area")));
        components.add(new JScrollPane(new JTable(new DefaultTableModel(new Object[][] { { 1, "Albert" } }, new Object[] { "ID", "Name" }))));

        final JPanel componentsPanel = new JPanel(new GridBagLayout());
        add(componentsPanel, BorderLayout.CENTER);

        for (int row = 0, size = components.size(); row < size; row++) {
            final JComponent component = components.get(row);

            final GridBagConstraints constraints = new GridBagConstraints();
            constraints.insets = new Insets(2, 2, 2, 2);
            constraints.gridy = row;
            constraints.anchor = GridBagConstraints.NORTHWEST;
            if (component instanceof JScrollPane) {
                constraints.weightx = 1;
                constraints.weighty = 1;
                constraints.fill = GridBagConstraints.BOTH;
            } else if (!(component instanceof JButton)) {
                constraints.fill = GridBagConstraints.BOTH;
            }
            componentsPanel.add(component, constraints);
        }
    }
}
