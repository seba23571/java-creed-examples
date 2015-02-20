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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Window;

import javax.swing.JWindow;

public class OverlayWindow extends JWindow {

  private static final long serialVersionUID = -1064019712895478018L;

  public OverlayWindow(final Window owner) {
    super(owner);
    setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
    setLayout(null);
  }

  @Override
  public void paint(final Graphics g) {
    super.paint(g);
    final Graphics2D g2 = (Graphics2D) g.create();
    paintComponent(g2);
    g2.dispose();
  }

  protected void paintComponent(final Graphics2D g2) {}
}