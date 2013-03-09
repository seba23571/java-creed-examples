package com.javacreed.examples.collections.map;

import java.util.Map;

public interface TimeMapAction {

  String getName();

  long timeAction(Map<String, String> list, int limit);
}
