/*
 * #%L
 * Change the Video Speed with VLC
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
package com.javacreed.examples.vlc;

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
  private Action changeSpeedAction;

  private File playingFile;

  @Override
  public Action getChangeSpeedAction() {
    return changeSpeedAction;
  }

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

    changeSpeedAction = new AbstractAction() {
      private static final long serialVersionUID = -4832583413812894248L;

      @Override
      public void actionPerformed(final ActionEvent e) {
        onChangePlayerSpeed();
      }
    };
    changeSpeedAction.setEnabled(false);
  }

  private void onChangePlayerSpeed() {
    final View view = getView();

    final VideoSpeed speed = view.getPlayerSpeed();
    if (speed == null) {
      return;
    }

    final EmbeddedMediaPlayer mediaPlayer = view.getMediaPlayer();
    if (mediaPlayer.setRate(speed.getRate()) != 0) {
      view.showWarning("Change Player Speed", "Failed to change tghe player speed");
    }
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

    final File directory = playingFile == null ? new File("") : playingFile.getParentFile();
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

    final String vlcLibPath = "vlc\\vlc-2.1.5";
    NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), vlcLibPath);

    initActions();
    view.init();
    view.showView(true);
  }

}
