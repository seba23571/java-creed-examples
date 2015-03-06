package com.javacreed.examples.io;

import java.util.concurrent.TimeUnit;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.NativeLibrary;

public class Example {

  public static void main(final String[] args) throws Exception {

    final String vlcLibPath = "C:\\Users\\Mona Lisa\\Downloads\\vlc-2.1.5";
    final String mediaPath = "\\\\192.168.75.25\\Data\\sd\\videos\\England\\2014_2015\\Premier_League\\Arsenal_vs._Aston_Villa\\908093_0\\Arsenal_vs._Aston_Villa.mp4";

    NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), vlcLibPath);
    final EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent = new EmbeddedMediaPlayerComponent();
    EmbeddedMediaPlayer embeddedMediaPlayer = embeddedMediaPlayerComponent.getMediaPlayer();
    embeddedMediaPlayer.prepareMedia(mediaPath);
    embeddedMediaPlayer.parseMedia();

    System.out.println("Length: " + embeddedMediaPlayer.getLength());
    System.out.println("Dimentions: " + embeddedMediaPlayer.getVideoDimension());
  }
}
