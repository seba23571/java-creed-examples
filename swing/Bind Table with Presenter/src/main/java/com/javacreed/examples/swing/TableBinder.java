/*
 * #%L
 * Bind Text Field with Presenter
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
package com.javacreed.examples.swing;

import java.util.Objects;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.EventListenerList;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class TableBinder extends AbstractBinder<JTable> {

    protected TableModel model;
    protected TableColumnModel columnModel;
    protected ListSelectionModel selectionModel;

    protected final EventListenerList listenerList = new EventListenerList();

    public TableBinder() {
        this(null, null, null);
    }

    public TableBinder(final TableModel model, final TableColumnModel columnModel, final ListSelectionModel selectionModel) {
        if (model == null) {
            this.model = createDefaultDataModel();
        } else {
            this.model = model;
        }

        if (columnModel == null) {
            this.columnModel = createDefaultColumnModel();
            createDefaultColumnsFromModel();
        } else {
            this.columnModel = columnModel;
        }

        if (selectionModel == null) {
            this.selectionModel = createDefaultSelectionModel();
        } else {
            this.selectionModel = selectionModel;
        }
    }

    public void addColumn(final TableColumn aColumn) {
        if (aColumn.getHeaderValue() == null) {
            final int modelColumn = aColumn.getModelIndex();
            final String columnName = getModel().getColumnName(modelColumn);
            aColumn.setHeaderValue(columnName);
        }
        getColumnModel().addColumn(aColumn);
    }

    @Override
    public JTable attach(final JTable component) {
        super.attach(component);
        component.setModel(getModel());
        component.setColumnModel(getColumnModel());
        component.setSelectionModel(getSelectionModel());
        return component;
    }

    protected TableColumnModel createDefaultColumnModel() {
        return new DefaultTableColumnModel();
    }

    public void createDefaultColumnsFromModel() {
        final TableModel model = getModel();
        if (model != null) {
            // Remove any current columns
            final TableColumnModel columnModel = getColumnModel();
            while (columnModel.getColumnCount() > 0) {
                columnModel.removeColumn(columnModel.getColumn(0));
            }

            // Create new columns from the data model info
            for (int i = 0; i < model.getColumnCount(); i++) {
                addColumn(new TableColumn(i));
            }
        }
    }

    protected TableModel createDefaultDataModel() {
        return new DefaultTableModel();
    }

    protected ListSelectionModel createDefaultSelectionModel() {
        return new DefaultListSelectionModel();
    }

    public <T extends TableColumnModel> T getColumnModel() {
        @SuppressWarnings("unchecked")
        final T t = (T) columnModel;
        return t;
    }

    public <T extends TableModel> T getModel() {
        @SuppressWarnings("unchecked")
        final T t = (T) model;
        return t;
    }

    public <T extends ListSelectionModel> T getSelectionModel() {
        @SuppressWarnings("unchecked")
        final T t = (T) selectionModel;
        return t;
    }

    public void setColumnModel(final TableColumnModel columnModel) {
        this.columnModel = Objects.requireNonNull(columnModel);
        if (isAttached()) {
            getComponent().setColumnModel(columnModel);
        }
    }

    public void setModel(final TableModel model) throws NullPointerException {
        this.model = Objects.requireNonNull(model);
        if (isAttached()) {
            getComponent().setModel(model);
        }
    }

    public void setSelectionModel(final ListSelectionModel selectionModel) {
        this.selectionModel = Objects.requireNonNull(selectionModel);
        if (isAttached()) {
            getComponent().setSelectionModel(selectionModel);
        }
    }
}
