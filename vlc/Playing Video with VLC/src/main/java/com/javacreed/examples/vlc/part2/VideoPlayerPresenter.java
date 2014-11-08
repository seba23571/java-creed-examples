package com.javacreed.examples.vlc.part2;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.Action;

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.NativeLibrary;

public class VideoPlayerPresenter implements Presenter {

  private View view;

  private Action openAction;
  private Action stopAction;

  private File playingFile;

  @Override
  public Action getOpenAction() {
    return openAction;
  }

  @Override
  public Action getStopAction() {
    return stopAction;
  }

  private View getView() {
    if (view == null) {
      throw new IllegalStateException("View is not set");
    }
    return view;
  }

  private void initActions() {
    openAction = new AbstractAction("Open") {
      private static final long serialVersionUID = -6693478787539778288L;

      @Override
      public void actionPerformed(final ActionEvent e) {
        onOpen();
      }
    };

    stopAction = new AbstractAction("Stop") {
      private static final long serialVersionUID = 5399863750784072072L;

      @Override
      public void actionPerformed(final ActionEvent e) {
        onStop();
      }
    };
    stopAction.setEnabled(false);
  }

  @Override
  public void onClose() {
    final View view = getView();
    view.getMediaPlayer().stop();
    view.showView(false);
    view.destroy();
  }

  private void onOpen() {
    final View view = getView();

    final File directory = playingFile == null ? new File("C:\\Users\\Albert\\Downloads\\Films\\Children\\Pixar 1")
        : playingFile.getParentFile();
    final File file = view.showOpenFileDialog(directory, playingFile);
    if (file != null) {
      playingFile = file;
      onPlayFile();
    }
  }

  private void onPlayFile() {
    final View view = getView();

    final EmbeddedMediaPlayer mediaPlayer = view.getMediaPlayer();
    mediaPlayer.playMedia(playingFile.getAbsolutePath());
    openAction.setEnabled(false);
    stopAction.setEnabled(true);
  }

  private void onStop() {
    final View view = getView();
    view.getMediaPlayer().stop();
    openAction.setEnabled(true);
    stopAction.setEnabled(false);
  }

  public void setView(final View view) {
    this.view = Objects.requireNonNull(view);
    this.view.setPresenter(this);
  }

  public void start() {
    final View view = getView();

    final String vlcLibPath = "C:\\Users\\Albert\\Downloads\\vlc-2.1.5-win64\\vlc-2.1.5";
    NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), vlcLibPath);

    initActions();
    view.init();
    view.showView(true);
  }

}
