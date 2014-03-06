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
package com.javacreed.swing.part2;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.SplashScreen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InteractiveSplashScreen {

  private static final Logger LOGGER = LoggerFactory.getLogger(InteractiveSplashScreen.class);

  private final SplashScreen splashScreen = SplashScreen.getSplashScreen();

  private volatile Font font = new Font("Arial", Font.PLAIN, 14);
  private volatile Color colour = Color.WHITE;

  private volatile double progress = 0;

  private Thread blinkThread;

  private void blink(final boolean show) {
    if (splashScreen == null || !splashScreen.isVisible()) {
      Thread.currentThread().interrupt();
      return;
    }

    final Dimension size = splashScreen.getSize();

    final int progressWidth = (int) ((size.width - 22) * progress);
    final Rectangle rect = new Rectangle(progressWidth + 9, size.height - 48, 2, 27);

    final Graphics2D g = splashScreen.createGraphics();
    g.setComposite(AlphaComposite.Clear);
    g.fill(rect);

    if (show) {
      InteractiveSplashScreen.LOGGER.debug("Blink (Process: {}%)", progress);
      g.setPaintMode();
      g.setColor(colour);
      g.fill(rect);
    }

    splashScreen.update();
  }

  public void setBlinkVisible(final boolean visible) {
    if (blinkThread != null) {
      blinkThread.interrupt();
    }

    if (visible) {
      blinkThread = new Thread(new Runnable() {
        @Override
        public void run() {
          boolean show = true;
          while (!Thread.currentThread().isInterrupted()) {
            try {
              blink(show = !show);
            } catch (final IllegalStateException e) {
              return;
            }

            try {
              Thread.sleep(300);
            } catch (final InterruptedException e) {
              Thread.currentThread().interrupt();
              return;
            }
          }
        }
      });
      blinkThread.start();
    }
  }

  public void setProgress(final double progress, final String message) {
    if (splashScreen != null && splashScreen.isVisible()) {

      this.progress = progress;

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
