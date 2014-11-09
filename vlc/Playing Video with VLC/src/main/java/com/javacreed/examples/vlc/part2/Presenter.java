package com.javacreed.examples.vlc.part2;

import javax.swing.Action;

public interface Presenter {

  Action getOpenAction();

  Action getStopAction();

  void onClose();

}
