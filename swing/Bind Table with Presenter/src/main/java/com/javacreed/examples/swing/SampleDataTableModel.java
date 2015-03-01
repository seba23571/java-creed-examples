package com.javacreed.examples.swing;

public class SampleDataTableModel extends ListTableModel<SampleData> {

    private static final long serialVersionUID = -7189967169037769616L;

    public SampleDataTableModel() {
        super(//@formatter:off
                new ColumnHelper<SampleData>("ID", Long.class) {@Override public Object getValue(final SampleData t, final int rowIndex) { return t.getId();}},
                new ColumnHelper<SampleData>("Name", String.class) {@Override public Object getValue(final SampleData t, final int rowIndex) { return t.getName();}},
                new ColumnHelper<SampleData>("Surname", String.class) {@Override public Object getValue(final SampleData t, final int rowIndex) { return t.getSurname();}}
             );//@formatter:on
    }
}
