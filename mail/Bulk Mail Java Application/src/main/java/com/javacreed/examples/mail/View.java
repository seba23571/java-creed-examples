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

import javax.swing.Action;
import javax.swing.filechooser.FileFilter;

import com.javacreed.examples.mail.sendtest.SendTestView;

public interface View {

  void destroy();

  String getEditorMessage();

  SendTestView getSendTestView();

  String getSubject();

  void init();

  void setEditorMessage(String message);

  void setPresenter(Presenter presenter);

  void setPreviewMessage(String message);

  void setStatus(String status);

  void setSubject(String subject);

  void showError(String title, String message);

  void showMessage(String title, String message);

  File showNewDialog(File currentDirectory, File currentMessageFile, FileFilter instance);

  File showOpenDialog(File currentDirectory, File currentFile, FileFilter filter);

  void showPane(ViewPane messagePane, Action... actions);

  File showSaveDialog(File currentDirectory, File currentFile, FileFilter filter);

  void showView(boolean show);

  void showWarn(String title, String message);
}
