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

import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.EventListenerList;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class ButtonBinder extends AbstractBinder<JTextField> {

  protected Document document;
  protected final EventListenerList listenerList = new EventListenerList();

  public ButtonBinder() {
    this(null);
  }

  public ButtonBinder(final Document document) {
    if (document == null) {
      this.document = createDefaultModel();
    } else {
      this.document = document;
    }
  }

  public void addActionListener(final ActionListener listener) {
    listenerList.add(ActionListener.class, listener);
    if (isAttached()) {
      getComponent().addActionListener(listener);
    }
  }

  @Override
  public JTextField attach(final JTextField component) {
    super.attach(component);
    component.setDocument(getDocument());

    for (final ActionListener listener : listenerList.getListeners(ActionListener.class)) {
      component.addActionListener(listener);
    }

    return component;
  }

  protected Document createDefaultModel() {
    return new PlainDocument();
  }

  public Document getDocument() {
    return document;
  }

  public String getText() {
    final Document doc = getDocument();
    String text;
    try {
      text = doc.getText(0, doc.getLength());
    } catch (final BadLocationException e) {
      text = null;
    }
    return text;
  }

  public int getTextLength() {
    return getText().length();
  }

  public void setDocument(final Document document) throws NullPointerException {
    this.document = Objects.requireNonNull(document);
    if (isAttached()) {
      getComponent().setDocument(document);
    }
  }

  public void setText(final String text) {
    try {
      final Document doc = getDocument();
      if (doc instanceof AbstractDocument) {
        ((AbstractDocument) doc).replace(0, doc.getLength(), text, null);
      } else {
        doc.remove(0, doc.getLength());
        doc.insertString(0, text, null);
      }
    } catch (final BadLocationException e) {
      if (isAttached()) {
        UIManager.getLookAndFeel().provideErrorFeedback(getComponent());
      }
    }
  }
}
