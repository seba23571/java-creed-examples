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

import javax.swing.DefaultComboBoxModel;

/**
 * Created by Albert on 07/02/2015.
 */
public class HeadersComboBoxModel extends DefaultComboBoxModel<String> {

  private static final long serialVersionUID = -7300163827024809481L;

  public void setDataHeaders(final String[] headers) {
    removeAllElements();
    for (final String header : headers) {
      addElement(header);
    }
  }
}
