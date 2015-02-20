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
package com.javacreed.examples.vlc.part3;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.NativeLibrary;

public class Example3 {
  public static void main(final String[] args) throws Exception {

    // Download the native libraries from http://download.videolan.org/pub/videolan/vlc/last/
    // Change the following paths
    final String vlcLibPath = "C:\\Users\\Albert\\Downloads\\vlc-2.1.5-win64\\vlc-2.1.5";
    final String mediaPath = "C:\\Users\\Albert\\Downloads\\Films\\Children\\Pixar 1\\Lifted.mkv";

    NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), vlcLibPath);

    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        final JFrame frame = new JFrame("Paint Over Video with VLC (Part 3)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setVisible(true);

        final Canvas canvas = new Canvas();
        frame.add(canvas, BorderLayout.CENTER);
        frame.setVisible(true);

        final MessageOverlayView view = new MessageOverlayView(frame);
        view.setMessageFont(new Font("Arial", Font.PLAIN, 22));
        view.setMessageColor(Color.WHITE);
        view.setMessage("Hello VLC");

        final Timer timer = new Timer(1000, new ActionListener() {
          @Override
          public void actionPerformed(final ActionEvent e) {
            view.setMessage(String.format("Hello VLC.  The Time is: %tT", System.currentTimeMillis()));
          }
        });
        timer.setRepeats(true);
        timer.start();

        final MediaPlayerFactory factory = new MediaPlayerFactory(new String[] { "--video-title=vlcj video output",
            "--no-snapshot-preview", "--quiet", "--quiet-synchro", "--sub-filter=logo:marq", "--intf=dummy" });
        final EmbeddedMediaPlayer mediaPlayer = factory.newEmbeddedMediaPlayer();
        mediaPlayer.setVideoSurface(factory.newVideoSurface(canvas));
        mediaPlayer.setOverlay(view);
        mediaPlayer.enableOverlay(true);
        mediaPlayer.playMedia(mediaPath);

        frame.addWindowListener(new WindowAdapter() {
          @Override
          public void windowClosing(final WindowEvent e) {
            try {
              timer.stop();
              mediaPlayer.release();
            } catch (final Exception ex) {/* Ignore */}
          }
        });
      }
    });
  }

}