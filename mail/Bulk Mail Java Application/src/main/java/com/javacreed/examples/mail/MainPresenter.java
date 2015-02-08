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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.swing.Action;
import javax.swing.ComboBoxModel;
import javax.swing.table.TableModel;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO: The file encoding is took for granted as UTF-8
 */
public class MainPresenter implements Presenter {

  public static final Logger LOGGER = LoggerFactory.getLogger(MainPresenter.class);

  private final Model model = new Model();

  private ExecutionContext executionContext;

  private View view;

  private Action openAction;
  private Action saveAction;
  private Action backAction;
  private Action nextAction;
  private Action sendAction;
  private Action stopAction;

  @Override
  public TableModel getDataTableModel() {
    return model.getDataTableModel();
  }

  @Override
  public ComboBoxModel<String> getHeadersComboBoxModel() {
    return model.getHeadersComboBoxModel();
  }

  @Override
  public TableModel getVariablesTableModel() {
    return model.getVariablesTableModel();
  }

  private void onSave(){
      final View view = PresenterUtils.requireNonNull(this.view, "view");
String message = view.getMessage();

File file = model.getCurrentMessageFile();
      if(file==null){
          // No file is open.
          file = view.showSaveDialog(model.getCurrentDirectory(), null);
          if(file==null){
              return;
          }
      }

      try {
          FileUtils.write(file, message, "UTF-8");
          view.setStatus("File Saved");
      } catch (IOException e) {
          LOGGER.error("Failed to save the message to file: {}", file, e);
          view.showError("Save", "Failed to save the message to file");
      }
  }

  private void initActions() {
    sendAction = PresenterUtils.createAction("Send", new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {}
    });

      saveAction = PresenterUtils.createAction("Save", new ActionListener() {
          @Override
          public void actionPerformed(final ActionEvent e) {onSave();}
      });


      openAction = PresenterUtils.createAction("Open", new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        onOpen();
      }
    });

    nextAction = PresenterUtils.createAction("Next", new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        onNext();
      }
    });

    backAction = PresenterUtils.createAction("Back", new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        onBack();
      }
    });

    stopAction = PresenterUtils.createAction("Stop", new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        onStop();
      }
    });
  }

  private void onBack() {
    switch (model.getShowingPane()) {
    case MESSAGE_PANE:
      break;
    case DATA_PANE:
      onShowMessagePane();
      break;
    }
  }

  @Override
  public void onClosing() {
    final View view = PresenterUtils.requireNonNull(this.view, "view");
    view.showView(false);
    view.destroy();
  }

  private void onNext() {
    switch (model.getShowingPane()) {
    case MESSAGE_PANE:
      onNextFromMessagePane();
      break;
    case DATA_PANE:
      onNextFromDataPane();
      break;
    }
  }

  private void onNextFromDataPane() {
    final View view = PresenterUtils.requireNonNull(this.view, "view");

    // Validate
    final Set<String> selectedColumns = new HashSet<>();
    final List<VariableColumnBinding> variablesBindings = model.getVariablesColumnBindings();
    for (final VariableColumnBinding variableBinding : variablesBindings) {
      if (variableBinding.getDataColumn() == null) {
        view.showWarn("Next", "Please make sure that all variables are bound to a column");
        return;
      }

      // TODO: this may be permitted
      if (!selectedColumns.add(variableBinding.getDataColumn())) {
        view.showWarn("Next", "The column '" + variableBinding.getDataColumn() + "' is bound more than once");
        return;
      }
    }

    onShowSendEmailPane();
  }

  private void onNextFromMessagePane() {
    final View view = PresenterUtils.requireNonNull(this.view, "view");

    final String message = view.getMessage();
    if (message.length() == 0) {
      view.showWarn("Next", "You cannot send a blank email");
      return;
    }

    model.setMessage(message);

    onShowDataPane();
  }

  private void onOpen() {
    final View view = PresenterUtils.requireNonNull(this.view, "view");

    final File selectedFile = view.showOpenDialog(model.getCurrentDirectory(), model.getCurrentMessageFile());
    if (selectedFile == null) {
      return;
    }

    if (!selectedFile.isFile()) {
      view.showWarn("Open", "The selected file is not a file");
      return;
    }

    try {
      final String text = FileUtils.readFileToString(selectedFile, "UTF-8");

      switch (model.getShowingPane()) {
      case MESSAGE_PANE:
        model.setMessage(text);
        model.setCurrentMessageFile(selectedFile);
        model.setCurrentDirectory(selectedFile.getParentFile());
        view.setMessage(model.getMessage());
        break;
      case DATA_PANE:
        model.setData(text);
        break;
      }

    } catch (final IOException e) {
      MainPresenter.LOGGER.error("Failed to read file {}", selectedFile, e);
      view.showWarn("Open", "Failed to read file");
    }
  }

  private void onShowDataPane() {
    final View view = PresenterUtils.requireNonNull(this.view, "view");
    view.showPane(model.setShowingPane(ViewPane.DATA_PANE), openAction, backAction, nextAction);
  }

  private void onShowMessagePane() {
    final View view = PresenterUtils.requireNonNull(this.view, "view");
    view.setMessage(model.getMessage());
    view.showPane(model.setShowingPane(ViewPane.MESSAGE_PANE), openAction, saveAction, nextAction);
  }

  private void onShowSendEmailPane() {
    final View view = PresenterUtils.requireNonNull(this.view, "view");
    view.showPane(model.setShowingPane(ViewPane.SEND_EMAIL_PANE), stopAction, sendAction);
  }

  private void onStop() {}

  private void registerDefaultErrorHandler() {
    final Thread.UncaughtExceptionHandler handler = Thread.currentThread().getUncaughtExceptionHandler();
    Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
      @Override
      public void uncaughtException(final Thread t, final Throwable e) {
        MainPresenter.LOGGER.error("Uncaught Exception", e);
        if (handler != null) {
          handler.uncaughtException(t, e);
        }

        if (view != null) {
          try {
            view.showError("Unknown Error",
                "A background process failed in an unexpected manner.  Please close the application and try again.\n"
                    + "\nShould this problem persist, please report this to Yarnwinder (yarnwinder@javacreed.com).");
          } catch (final RuntimeException re) {}
        }
      }
    });
  }

  public void setExecutionContext(final ExecutionContext executionContext) {
    this.executionContext = Objects.requireNonNull(executionContext);
  }

  public void setView(final View view) {
    this.view = Objects.requireNonNull(view);
    view.setPresenter(this);
  }

  public void start() {
    final View view = PresenterUtils.requireNonNull(this.view, "view");

    registerDefaultErrorHandler();

    model.setDataTableModel(new DataTableModel());
    model.setVariablesTableModel(new VariablesTableModel());
    model.setHeadersComboBoxModel(new HeadersComboBoxModel());
    model.setMessage("");

    initActions();
    view.init();
    view.showView(true);

    onShowMessagePane();
  }
}
