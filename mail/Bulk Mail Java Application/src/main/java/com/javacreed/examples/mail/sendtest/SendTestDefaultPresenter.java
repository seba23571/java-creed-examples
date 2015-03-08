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
package com.javacreed.examples.mail.sendtest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.swing.Action;

import com.javacreed.examples.mail.Email;
import com.javacreed.examples.mail.Model;
import com.javacreed.examples.mail.SendMailWorker;
import com.javacreed.examples.mail.SwingUtils;

public class SendTestDefaultPresenter implements SendTestPresenter {

  private SendTestView view;
  private SendMailWorker sendMailWorker;
  private Model model;

  private Action cancelAction;
  private Action sendAction;

  private void close() {
    view.showView(false);
    view.destroy();
  }

  @Override
  public Action getCancelAction() {
    return cancelAction;
  }

  @Override
  public Action getSendAction() {
    return sendAction;
  }

  @Override
  public Set<String> getVariablesNames() {
    return model.getVariablesNames();
  }

  private void initActions() {
    cancelAction = SwingUtils.createAction("Cancel", new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {

      }
    });

    sendAction = SwingUtils.createAction("Send", new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        onSend();
      }
    });
  }

  @Override
  public void onClosing() {

  }

  private void onSend() {
    if (sendMailWorker != null) {
      view.showWarn("Send Test Email",
          "Please wait for the current action to complete before you send the next test email");
      return;
    }

    final Map<String, String> params = model.createParameters();

    for (final Map.Entry<String, String> entry : params.entrySet()) {
      entry.setValue(view.getValueFor(entry.getKey()));
    }

    final Email email = model.createEmail(params);

    sendAction.setEnabled(false);
    sendMailWorker = new SendMailWorker(email) {
      @Override
      protected void done() {
        sendAction.setEnabled(true);
        sendMailWorker = null;

        close();
      }
    };
    sendMailWorker.execute();
  }

  public void setModel(final Model model) {
    this.model = model;
  }

  public void setView(final SendTestView view) {
    this.view = Objects.requireNonNull(view);
    this.view.setPresenter(this);
  }

  public void start() {
    initActions();

    view.init();
    view.showView(true);
  }
}
