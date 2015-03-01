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

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class ApplicationPresenter implements Presenter {

  private View view;
  private final ButtonBinder nameFieldBinder = new ButtonBinder();

  @Override
  public ButtonBinder getNameFieldBinder() {
    return nameFieldBinder;
  }

  @Override
  public void onClosing() {
    view.setVisible(false);
    view.dispose();
  }

  public void onNameEntered() {
    final String name = nameFieldBinder.getText();
    view.showMessage("Hello", "Hello " + name);
  }

  public void setView(final View view) {
    this.view = view;
  }

  public void start() {
    nameFieldBinder.setText("Hello");
    nameFieldBinder.addActionListener(new AbstractAction("Some Action") {
      private static final long serialVersionUID = 7248381518813305225L;

      @Override
      public void actionPerformed(final ActionEvent e) {
        onNameEntered();
      }
    });

    view.init();
    view.setVisible(true);
  }

  public void useDefaults() {
    setView(new Application(this));
  }

}
