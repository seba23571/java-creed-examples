package com.javacreed.examples.io;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.NativeLibrary;

public class Example2 {

  public static void main(final String[] args) throws Exception {

    final String vlcLibPath = "C:\\Users\\Mona Lisa\\Downloads\\vlc-2.1.5";
    final String mediaPath = "\\\\192.168.75.25\\Data\\sd\\videos\\England\\2014_2015\\Premier_League\\Arsenal_vs._Aston_Villa\\908093_0\\Arsenal_vs._Aston_Villa.mp4";

    NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), vlcLibPath);

    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        final JFrame frame = new JFrame("Playing Video with VLC (Part 1)");
        final EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        frame.setContentPane(embeddedMediaPlayerComponent);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setVisible(true);

        final EmbeddedMediaPlayer mediaPlayer = embeddedMediaPlayerComponent.getMediaPlayer();
        mediaPlayer.playMedia(mediaPath);

        System.out.println("Length: " + mediaPlayer.getLength());
        System.out.println("Dimentions: " + mediaPlayer.getVideoDimension());

        frame.addWindowListener(new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent e) {
            try {
              mediaPlayer.release();
            } catch (Exception ex) {/* Ignore */}
          }
        });
      }
    });
  }
}
