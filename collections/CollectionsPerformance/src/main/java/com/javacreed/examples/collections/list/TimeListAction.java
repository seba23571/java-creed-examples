package com.javacreed.examples.collections.list;

import java.util.List;

public interface TimeListAction {

  String getName();
  
	long timeAction(List<String> list, int limit);
}
