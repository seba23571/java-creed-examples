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

import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import javax.swing.text.html.HTMLDocument;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlEditorModel {

  private static void setText(final Document doc, final String text) {
    try {
      if (doc instanceof AbstractDocument) {
        ((AbstractDocument) doc).replace(0, doc.getLength(), text, null);
      } else {
        doc.remove(0, doc.getLength());
        doc.insertString(0, text, null);
      }
    } catch (final BadLocationException e) {}
  }

  private static final Logger LOGGER = LoggerFactory.getLogger(HtmlEditorModel.class);
  private final PlainDocument plainDocument = new PlainDocument();

  private final HTMLDocument htmlDocument = new HTMLDocument();

  public HTMLDocument getHtmlDocument() {
    return htmlDocument;
  }

  public PlainDocument getPlainDocument() {
    return plainDocument;
  }

  public String getText() {
    String txt;
    try {
      txt = plainDocument.getText(0, plainDocument.getLength());
    } catch (final BadLocationException e) {
      txt = null;
    }
    return txt;
  }

  public void setText(final String text) {

    HtmlEditorModel.setText(plainDocument, text);
    HtmlEditorModel.setText(htmlDocument, text);
  }
}
