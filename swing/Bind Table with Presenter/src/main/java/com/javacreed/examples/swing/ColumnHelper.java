package com.javacreed.examples.swing;

public abstract class ColumnHelper<T> {

    private final String name;
    private final Class<?> type;

    public ColumnHelper(final String name, final Class<?> type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }

    public abstract Object getValue(final T t, final int rowIndex);

    public boolean isEditable(final T t, final int rowIndex) {
        return false;
    }

    public void setValue(final T t, final Object value) {}
}