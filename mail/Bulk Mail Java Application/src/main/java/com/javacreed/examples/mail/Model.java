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
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;

public class Model {

  public static File getParent(final File file) {
    if (file == null) {
      return null;
    }

    return file.getParentFile();
  }

  private ViewPane showingPane;
  private Composer composer = new Composer();
  private File currentComposerFile;
  private File currentDataFile;
  private DataTableModel dataTableModel;
  private VariablesTableModel variablesTableModel;
  private DataParser dataParser;

  private HeadersComboBoxModel headersComboBoxModel;

  public Email createEmail(final int index) {
    final String recipient = "";
    final String subject = replaceVariablesWithData(getSubject(), index);
    final String message = replaceVariablesWithData(getMessage(), index);

    return new Email(recipient, subject, message);
  }

  public Email createEmail(final Map<String, String> parameters) {
    return composer.createEmail(parameters);
  }

  public Map<String, String> createParameters() {
    return composer.createParameters();
  }

  public List<Email> formatEmails() {
    return Collections.emptyList();
  }

  public File getCurrentComposerDirectory() {
    return Model.getParent(currentComposerFile);
  }

  public File getCurrentComposerFile() {
    return currentComposerFile;
  }

  public File getCurrentDataDirectory() {
    return Model.getParent(currentDataFile);
  }

  public File getCurrentDataFile() {
    return currentDataFile;
  }

  public DataTableModel getDataTableModel() {
    return dataTableModel;
  }

  public HeadersComboBoxModel getHeadersComboBoxModel() {
    return headersComboBoxModel;
  }

  public String getMessage() {
    return composer.getMessage();
  }

  public ViewPane getShowingPane() {
    return showingPane;
  }

  public String getSubject() {
    return composer.getSubject();
  }

  public List<VariableColumnBinding> getVariablesColumnBindings() {
    return variablesTableModel.getValues();
  }

  public Set<String> getVariablesNames() {
    return composer.getVariablesNames();
  }

  public VariablesTableModel getVariablesTableModel() {
    return variablesTableModel;
  }

  public void loadComposer(final File currentComposerFile) throws IOException, ClassNotFoundException {
    this.currentComposerFile = currentComposerFile.getAbsoluteFile();
    composer = Composer.readFromFile(currentComposerFile);
    variablesTableModel.setVariables(composer.getVariablesNames());
  }

  public void loadData(final File dataFile) throws IOException {
    this.currentDataFile = dataFile.getAbsoluteFile();
    setData(FileUtils.readFileToString(dataFile, "UTF-8"));
  }

  public String replaceVariablesWithData(String data, final int index) {
    // TODO: this is not cheap and need to see a better way
    for (final VariableColumnBinding binding : getVariablesColumnBindings()) {
      if (data.contains("${" + binding.getVariableName() + "}")) {
        final String dataColumn = binding.getDataColumn();
        final String value;
        if (dataColumn == null) {
          value = binding.getVariableName();
        } else {
          value = getDataTableModel().getCellValueAt(index, dataColumn);
        }
        data = data.replaceAll("\\$\\{" + binding.getVariableName() + "\\}", value);
      }
    }

    return data;
  }

  public void save() throws IOException {
    composer.saveToFile(currentComposerFile);
  }

  public void setCurrentComposerFile(final File currentComposerFile) {
    this.currentComposerFile = currentComposerFile;
  }

  public void setCurrentDataFile(final File currentDataFile) {
    this.currentDataFile = currentDataFile.getAbsoluteFile();
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
    composer.setMessage(message);
    variablesTableModel.setVariables(composer.getVariablesNames());
  }

  public ViewPane setShowingPane(final ViewPane showingPane) {
    return this.showingPane = showingPane;
  }

  public void setSubject(final String subject) {
    this.composer.setSubject(subject);
  }

  public void setVariablesTableModel(final VariablesTableModel variablesTableModel) {
    this.variablesTableModel = variablesTableModel;
  }
}
