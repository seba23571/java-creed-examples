package com.javacreed.api.csv.swing;

import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.junit.Test;

public class SwingExperiment {

  @Test
  private void test() {

    final TableModel tableModel = new DefaultTableModel();

    final SwingWorker<Integer, Object> worker = new SwingWorker<Integer, Object>() {

      @Override
      protected Integer doInBackground() throws Exception {

        return null;
      }
    };

  }

}
