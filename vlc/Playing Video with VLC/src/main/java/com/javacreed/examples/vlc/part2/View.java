package com.javacreed.examples.vlc.part2;

import java.io.File;

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public interface View {

  void destroy();

  EmbeddedMediaPlayer getMediaPlayer();

  void init();

  void setPresenter(Presenter presenter);

  void showError(String title, String message);

  void showMessage(String title, String message);

  File showOpenFileDialog(File directory, File selectedFile);

  void showView(boolean show);

  void showWarning(String title, String message);

}
