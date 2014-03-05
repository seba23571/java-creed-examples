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

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

  public static void main(final String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          final InteractiveSplashScreen splashScreen = new InteractiveSplashScreen();

          splashScreen.setProgress(0.2, "Loading data...");
          Thread.sleep(1000);

          splashScreen.setProgress(0.3, "Loading more data...");
          Thread.sleep(1000);

          splashScreen.setProgress(0.8, "Loading application...");
          final JFrame application = new JFrame();
          application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          application.setTitle("Build An Interactive Splash Screen");
          application.setSize(600, 400);
          application.setVisible(true);

          splashScreen.setProgress(1, "Loading complete");
        } catch (final InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    });
  }
}
