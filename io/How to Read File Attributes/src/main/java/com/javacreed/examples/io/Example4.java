package com.javacreed.examples.io;

import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.NativeLibrary;

public class Example4 {

  public static void main(final String[] args) throws Exception {

    final String vlcLibPath = "C:\\Users\\Mona Lisa\\Downloads\\vlc-2.1.5";
    // final String mediaPath = "\\\\192.168.75.25\\Data\\sd\\videos\\England\\2014_2015\\Premier_League\\Arsenal_vs._Aston_Villa\\908093_0\\Arsenal_vs._Aston_Villa.mp4";
    final String mediaPath = "\\\\192.168.75.25\\Data\\sd\\videos\\Argentina\\2014\\Primera_A\\Arsenal_de_Sarandí_vs._Banfield\\937560_0\\Arsenal_de_Sarandí_vs._Banfield.mp4";
    NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), vlcLibPath);

    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        final EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent = new EmbeddedMediaPlayerComponent();

        final JFrame application = new JFrame("Test");
        application.setContentPane(embeddedMediaPlayerComponent);
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setExtendedState(Frame.MAXIMIZED_BOTH);
        application.setVisible(true);

        final EmbeddedMediaPlayer embeddedMediaPlayer = embeddedMediaPlayerComponent.getMediaPlayer();
        embeddedMediaPlayer.playMedia(mediaPath);

        final MediaPlayerEventListener listener = new MediaPlayerEventAdapter() {
          @Override
          public void positionChanged(final MediaPlayer mediaPlayer, final float newPosition) {
            final Dimension dimension = mediaPlayer.getVideoDimension();
            if (dimension != null) {
              System.out.printf("%d X %d%n", dimension.width, dimension.height);
              embeddedMediaPlayer.removeMediaPlayerEventListener(this);
              application.setVisible(false);
              application.dispose();
            }
          }
        };

        embeddedMediaPlayer.addMediaPlayerEventListener(listener);
      }
    });

  }
}
