/*
 * #%L
 * Playing Video with VLC
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2012 - 2014 Java Creed
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
package com.javacreed.examples.vlc.part2;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.NativeLibrary;

public class Example2 {
  private static class OverlayWindow extends JWindow {

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
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setFont(g2.getFont().deriveFont(22F));
      g2.setColor(Color.WHITE);
      g2.drawString("Hello VLC", 20, 50);
    }
  }

  public static void main(final String[] args) throws Exception {

    // Download the native libraries from http://download.videolan.org/pub/videolan/vlc/last/
    // Change the following paths
    final String vlcLibPath = "C:\\Users\\Albert\\Downloads\\vlc-2.1.5-win64\\vlc-2.1.5";
    final String mediaPath = "C:\\Users\\Albert\\Downloads\\Films\\Children\\Pixar 1\\Lifted.mkv";

    NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), vlcLibPath);

    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        final JFrame frame = new JFrame("Paint Over Video with VLC (Part 2)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setVisible(true);

        final Canvas canvas = new Canvas();
        frame.add(canvas, BorderLayout.CENTER);
        frame.setVisible(true);

        final MediaPlayerFactory factory = new MediaPlayerFactory(new String[] { "--video-title=vlcj video output",
            "--no-snapshot-preview", "--quiet", "--quiet-synchro", "--sub-filter=logo:marq", "--intf=dummy" });
        final EmbeddedMediaPlayer mediaPlayer = factory.newEmbeddedMediaPlayer();
        mediaPlayer.setVideoSurface(factory.newVideoSurface(canvas));
        mediaPlayer.setOverlay(new OverlayWindow(frame));
        mediaPlayer.enableOverlay(true);
        mediaPlayer.playMedia(mediaPath);

        frame.addWindowListener(new WindowAdapter() {
          @Override
          public void windowClosing(final WindowEvent e) {
            try {
              mediaPlayer.release();
            } catch (final Exception ex) {/* Ignore */}
          }
        });
      }
    });
  }

}