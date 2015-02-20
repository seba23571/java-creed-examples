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
package com.javacreed.examples.vlc.part1;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.NativeLibrary;

public class Example1 {
  public static void main(final String[] args) throws Exception {

    // Download the native libraries from http://download.videolan.org/pub/videolan/vlc/last/
    // Change the following paths
    final String vlcLibPath = "C:\\Users\\Albert\\Downloads\\vlc-2.1.5-win64\\vlc-2.1.5";
    final String mediaPath = "C:\\Users\\Albert\\Downloads\\Films\\Children\\Pixar 1\\Lifted.mkv";

    NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), vlcLibPath);

    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        final EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent = new EmbeddedMediaPlayerComponent();

        final JFrame frame = new JFrame("Paint Over Video with VLC (Part 1)");
        frame.setContentPane(embeddedMediaPlayerComponent);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setVisible(true);

        final EmbeddedMediaPlayer mediaPlayer = embeddedMediaPlayerComponent.getMediaPlayer();
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