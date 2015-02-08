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
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Created by Albert on 06/02/2015.
 */
public class PresenterUtils {

  public static Action createAction(final String name, final ActionListener listener) {
    final Action action = new AbstractAction(name, SwingUtils.getIcon(name)) {

      private static final long serialVersionUID = -4972776849245710938L;

      @Override
      public void actionPerformed(final ActionEvent e) {
        listener.actionPerformed(e);
      }
    };

    return action;
  }

  public static <T> T requireNonNull(final T t, final String name) {
    if (t == null) {
      throw new IllegalStateException("The field " + name + " is not set");
    }
    return t;
  }

  private PresenterUtils() {}
}
