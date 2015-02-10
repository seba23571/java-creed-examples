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

/**
 * Created by Albert on 06/02/2015.
 */
public interface View {

  void destroy();

  String getEditorMessage();

  void init();

  void setEditorMessage(String message);

  void setPresenter(Presenter presenter);

  void setPreviewMessage(String message);

  void setStatus(String status);

  void showError(String title, String message);

  File showOpenDialog(File currentDirectory, File currentFile);

  void showPane(ViewPane messagePane, Action... actions);

  File showSaveDialog(File currentDirectory, File currentFile);

  void showView(boolean show);

  void showWarn(String title, String message);
}
