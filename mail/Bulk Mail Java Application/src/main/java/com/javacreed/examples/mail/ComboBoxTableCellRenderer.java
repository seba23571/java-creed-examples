/*
 * #%L
 * Bulk Mail Java Application
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
package com.javacreed.examples.mail;

import java.awt.Component;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * Created by Albert on 07/02/2015.
 */
public class ComboBoxTableCellRenderer<T> extends JComboBox<T> implements TableCellRenderer {

  private static final long serialVersionUID = 1966887688810575977L;

  public ComboBoxTableCellRenderer(final ComboBoxModel<T> model) {
    super(model);
  }

  @Override
  public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected,
      final boolean hasFocus, final int row, final int column) {
    setSelectedItem(value);
    return this;
  }
}
