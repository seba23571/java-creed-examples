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
package com.javacreed.swing.part1;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.SplashScreen;

public class InteractiveSplashScreen {

  private final SplashScreen splashScreen = SplashScreen.getSplashScreen();

  private volatile Font font = new Font("Arial", Font.PLAIN, 14);
  private volatile Color colour = Color.WHITE;

  public void setProgress(final double progress, final String message) {
    if (splashScreen != null && splashScreen.isVisible()) {

      final Dimension size = splashScreen.getSize();
      final Graphics2D g = splashScreen.createGraphics();
      g.setComposite(AlphaComposite.Clear);
      g.fillRect(0, 0, size.width, size.height);
      g.setPaintMode();
      g.setColor(colour);

      final int progressWidth = Math.max((int) ((size.width - 22) * progress) - 5, 0);

      g.drawRect(10, size.height - 50, size.width - 20, 30);
      g.fillRect(12, size.height - 48, progressWidth, 27);

      g.setFont(font);
      g.drawString(message, 10, size.height - 52);

      splashScreen.update();
    }
  }

}
