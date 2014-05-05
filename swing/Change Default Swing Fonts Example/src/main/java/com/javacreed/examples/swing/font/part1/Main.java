package com.javacreed.examples.swing.font.part1;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FontUtils.adjustDefaultFontSize(+4);
                
                final Application app = new Application();
                app.setTitle("Change Default Swing Fonts Example");
                app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                app.pack();
                app.setVisible(true);
            }
        });
    }
}
