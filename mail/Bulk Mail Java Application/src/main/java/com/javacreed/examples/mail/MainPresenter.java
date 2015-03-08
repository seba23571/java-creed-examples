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
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.swing.Action;
import javax.swing.ComboBoxModel;
import javax.swing.table.TableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javacreed.examples.mail.sendtest.SendTestDefaultPresenter;

/**
 * TODO: The file encoding is took for granted as UTF-8
 */
public class MainPresenter implements Presenter {

  public static final Logger LOGGER = LoggerFactory.getLogger(MainPresenter.class);

  private final Model model = new Model();

  @SuppressWarnings("unused")
  private ExecutionContext executionContext;

  private View view;

  private Action newAction;
  private Action openAction;
  private Action saveAction;
  private Action backAction;
  private Action nextAction;
  private Action sendAction;
  private Action sendTestAction;
  private Action stopAction;

  private SendMailWorker sendMailWorker;

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

  private void initActions() {
    newAction = SwingUtils.createAction("New", new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        onNew();
      }
    });

    sendAction = SwingUtils.createAction("Send", new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        onSend();
      }
    });

    sendTestAction = SwingUtils.createAction("Send Test", new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        onSendTest();
      }
    });

    saveAction = SwingUtils.createAction("Save", new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        onSave();
      }
    });

    openAction = SwingUtils.createAction("Open", new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        onOpen();
      }
    });

    nextAction = SwingUtils.createAction("Next", new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        onNext();
      }
    });

    backAction = SwingUtils.createAction("Back", new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        onBack();
      }
    });

    stopAction = SwingUtils.createAction("Stop", new ActionListener() {
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
    view.showView(false);
    view.destroy();
  }

  @Override
  public void onDataValueChanged(final int index) {
    final Email email = model.createEmail(index);
    view.setPreviewMessage(email.getMessage());
  }

  private void onNew() {
    File selectedFile = view.showNewDialog(model.getCurrentComposerDirectory(), model.getCurrentComposerFile(),
        ComposerFileFilter.INSTANCE);
    if (selectedFile == null) {
      return;
    }

    selectedFile = selectedFile.getAbsoluteFile();

    // Add the '.composer' extension if missing
    if (!selectedFile.getName().endsWith(".composer")) {
      selectedFile = new File(selectedFile.getParent(), selectedFile.getName() + ".composer");
    }

    // TODO: check if the file exists and warn the user before continuing
    model.setMessage("");
    model.setCurrentComposerFile(selectedFile);
    view.setEditorMessage(model.getMessage());
    view.setStatus("Working on file: " + selectedFile.getName() + " (found in folder: "
        + selectedFile.getParentFile().getAbsolutePath() + ")");
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

    final String message = view.getEditorMessage();
    if (message.length() == 0) {
      view.showWarn("Next", "You cannot send a blank email");
      return;
    }

    model.setMessage(message);
    onShowDataPane();
  }

  private void onOpen() {

    try {

      switch (model.getShowingPane()) {
      case MESSAGE_PANE:
        onOpenComposerFile();
        break;
      case DATA_PANE:
        onOpenDataFile();
        break;
      }

    } catch (final ClassNotFoundException e) {
      MainPresenter.LOGGER.error("Failed to read file", e);
      view.showWarn("Open", "Failed to loadComposer file");
    } catch (final IOException e) {
      MainPresenter.LOGGER.error("Failed to read file", e);
      view.showWarn("Open", "Failed to read file");
    }
  }

  private void onOpenComposerFile() throws IOException, ClassNotFoundException {
    final File selectedFile = view.showOpenDialog(model.getCurrentComposerDirectory(), model.getCurrentComposerFile(),
        ComposerFileFilter.INSTANCE);
    if (selectedFile == null) {
      return;
    }

    if (!selectedFile.isFile()) {
      view.showWarn("Open", "The selected file is not a file");
      return;
    }

    model.loadComposer(selectedFile);

    view.setSubject(model.getSubject());
    view.setEditorMessage(model.getMessage());
    view.setStatus("Working on file: " + selectedFile.getName() + " (found in folder: "
        + selectedFile.getParentFile().getAbsolutePath() + ")");
  }

  private void onOpenDataFile() throws IOException {
    // TODO: add a CSV file filter
    final File selectedFile = view.showOpenDialog(model.getCurrentDataDirectory(), model.getCurrentDataFile(), null);
    if (selectedFile == null) {
      return;
    }

    if (!selectedFile.isFile()) {
      view.showWarn("Open", "The selected file is not a file");
      return;
    }

    model.loadData(selectedFile);
    view.setStatus("Working on file: " + selectedFile.getName() + " (found in folder: "
        + selectedFile.getParentFile().getAbsolutePath() + ")");
  }

  private void onSave() {
    final String message = view.getEditorMessage();

    File file = model.getCurrentComposerFile();
    if (file == null) {
      // No file is open.
      file = view.showSaveDialog(model.getCurrentComposerDirectory(), null, null);
      if (file == null) {
        return;
      }

      model.setCurrentComposerFile(file);
    }

    try {
      model.setSubject(view.getSubject());
      model.setMessage(view.getEditorMessage());
      model.save();
      view.setStatus("File Saved");
    } catch (final IOException e) {
      MainPresenter.LOGGER.error("Failed to save the message to file: {}", file, e);
      view.showError("Save", "Failed to save the message to file");
    }
  }

  private void onSend() {
    if (sendMailWorker != null) {
      view.showWarn("Send Email", "Please note that the emails are being sent");
      return;
    }

    final List<Email> emails = new LinkedList<>();

    sendMailWorker = new SendMailWorker(emails) {
      @Override
      protected void done() {
        view.showMessage("Send Email", "The emails were sent");
        sendMailWorker = null;
      }
    };
    sendMailWorker.execute();
  }

  private void onSendTest() {
    final String message = view.getEditorMessage();
    if (message.length() == 0) {
      view.showWarn("Send Test Email", "You cannot send a blank test email");
      return;
    }
    model.setMessage(message);

    final SendTestDefaultPresenter presenter = new SendTestDefaultPresenter();
    presenter.setView(view.getSendTestView());
    presenter.setModel(model);
    presenter.start();

  }

  private void onShowDataPane() {
    view.setPreviewMessage(model.getMessage());
    view.showPane(model.setShowingPane(ViewPane.DATA_PANE), openAction, backAction, nextAction);
  }

  private void onShowMessagePane() {
    view.setEditorMessage(model.getMessage());
    view.showPane(model.setShowingPane(ViewPane.MESSAGE_PANE), newAction, openAction, saveAction, sendTestAction,
        nextAction);
  }

  private void onShowSendEmailPane() {
    view.showPane(model.setShowingPane(ViewPane.SEND_EMAIL_PANE), stopAction, sendAction);
  }

  private void onStop() {

    if (sendMailWorker == null) {
      return;
    }

    sendMailWorker.cancel(true);
    sendMailWorker = null;
  }

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
