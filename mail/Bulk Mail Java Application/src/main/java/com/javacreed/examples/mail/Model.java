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

import java.io.File;
import java.util.List;

/**
 * Created by Albert on 06/02/2015.
 */
public class Model {

  private ViewPane showingPane;
  private MessageParser messageParser;
  private File currentMessageFile;
  private File currentDataFile;
  private File currentDirectory;
  private DataTableModel dataTableModel;
  private VariablesTableModel variablesTableModel;
  private DataParser dataParser;
  private HeadersComboBoxModel headersComboBoxModel;

  public File getCurrentDirectory() {
    return currentDirectory;
  }

  public File getCurrentMessageFile() {
    return currentMessageFile;
  }

  public DataTableModel getDataTableModel() {
    return dataTableModel;
  }

  public HeadersComboBoxModel getHeadersComboBoxModel() {
    return headersComboBoxModel;
  }

  public String getMessage() {
    return messageParser.getMessage();
  }

  public ViewPane getShowingPane() {
    return showingPane;
  }

  public List<VariableColumnBinding> getVariablesColumnBindings() {
    return variablesTableModel.getValues();
  }

  public VariablesTableModel getVariablesTableModel() {
    return variablesTableModel;
  }

  public void setCurrentDirectory(final File currentDirectory) {
    this.currentDirectory = currentDirectory;
  }

  public void setCurrentMessageFile(final File currentMessageFile) {
    this.currentMessageFile = currentMessageFile;
  }

  public void setData(final String data) {
    this.dataParser = new DataParser(data);
    this.dataTableModel.setData(dataParser.getHeaders(), dataParser.getValues());
    this.headersComboBoxModel.setDataHeaders(dataParser.getHeaders());
  }

  public void setDataTableModel(final DataTableModel dataTableModel) {
    this.dataTableModel = dataTableModel;
  }

  public void setHeadersComboBoxModel(final HeadersComboBoxModel headersComboBoxModel) {
    this.headersComboBoxModel = headersComboBoxModel;
  }

  public void setMessage(final String message) {
    messageParser = new MessageParser(message);
    variablesTableModel.setVariables(messageParser.getVariablesNames());
  }

  public ViewPane setShowingPane(final ViewPane showingPane) {
    return this.showingPane = showingPane;
  }

  public void setVariablesTableModel(final VariablesTableModel variablesTableModel) {
    this.variablesTableModel = variablesTableModel;
  }

    public File getCurrentDataFile() {
        return currentDataFile;
    }

    public void setCurrentDataFile(File currentDataFile) {
        this.currentDataFile = currentDataFile;
    }
}
