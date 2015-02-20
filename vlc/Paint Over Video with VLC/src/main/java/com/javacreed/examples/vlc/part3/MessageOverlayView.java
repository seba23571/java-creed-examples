/*
 * #%L
 * Paint Over Video with VLC
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
package com.javacreed.examples.vlc.part3;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Window;
import java.util.Objects;

import javax.swing.UIManager;

public class MessageOverlayView extends OverlayWindow {

  private static final long serialVersionUID = -8162955249981465858L;

  private Font messageFont = UIManager.getFont("Label.font");;
  private Color messageColor = UIManager.getColor("Label.foreground");
  private String message = "Hello";

  public MessageOverlayView(final Window owner) {
    super(owner);
  }

  @Override
  protected void paintComponent(final Graphics2D g2) {
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setFont(messageFont);
    g2.setColor(messageColor);

    final FontMetrics fm = g2.getFontMetrics();
    final int messageWidth = fm.stringWidth(message);
    final int messageHeight = fm.getHeight();

    final int x = (getWidth() - messageWidth) / 2;
    final int y = (getHeight() - messageHeight) / 2;

    g2.drawString(message, x, y);
  }

  public void setMessage(final String message) {
    this.message = Objects.requireNonNull(message);
    repaint();
  }

  public void setMessageColor(final Color messageColor) {
    this.messageColor = Objects.requireNonNull(messageColor);
    repaint();
  }

  public void setMessageFont(final Font messageFont) {
    this.messageFont = Objects.requireNonNull(messageFont);
    repaint();
  }

}
