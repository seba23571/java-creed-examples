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
package com.javacreed.examples.deadlock.part6;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Example2 {

  public static void main(final String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        final String message = "Always launch GUI from event dispatcher thread";

        final JLabel label = new JLabel(message);
        label.setFont(new Font("Arial", Font.BOLD, 22));

        final JFrame app = new JFrame();
        app.setTitle(message);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.add(label);
        app.pack();
        app.setLocationRelativeTo(null);
        app.setResizable(false);
        app.setVisible(true);
      }
    });
  }
}
