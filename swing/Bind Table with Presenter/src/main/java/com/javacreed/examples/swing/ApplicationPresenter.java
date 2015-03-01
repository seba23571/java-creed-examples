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

public class ApplicationPresenter implements Presenter {

    private View view;
    private final TableBinder dataFieldBinder = new TableBinder(new SampleDataTableModel(), null, null);

    @Override
    public TableBinder getDataFieldBinder() {
        return dataFieldBinder;
    }

    @Override
    public void onClosing() {
        view.setVisible(false);
        view.dispose();
    }

    public void setView(final View view) {
        this.view = view;
    }

    public void start() {
        // Add sample data
        final SampleDataTableModel tableModel = dataFieldBinder.getModel();
        tableModel.add(new SampleData(1, "Albert", "Attard"));

        view.init();
        view.setVisible(true);
    }

    public void useDefaults() {
        setView(new Application(this));
    }

}
