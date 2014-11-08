package com.javacreed.examples.vlc.part2;

import javax.swing.SwingUtilities;

public class Main {
  public static void main(final String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        final VideoPlayerPresenter presenter = new VideoPlayerPresenter();
        presenter.setView(new VideoPlayerApplication());
        presenter.start();
      }
    });
  }
}
