package com.javacreed.examples.swing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ListTableModel<T> extends AbstractTableModel implements Iterable<T> {

    private static final long serialVersionUID = 602698612833024133L;

    protected final List<T> rows = new ArrayList<>();

    private final List<T> unmodifiableRows = Collections.unmodifiableList(rows);

    private final List<ColumnHelper<T>> columns;

    public ListTableModel(final Collection<ColumnHelper<T>> columns) {
        this.columns = new ArrayList<>(columns);
    }

    @SafeVarargs
    public ListTableModel(final ColumnHelper<T>... columns) {
        this(Arrays.asList(columns));
    }

    public <E extends T> void add(final E entry) {
        final int index = rows.size();
        rows.add(entry);
        fireTableRowsInserted(index, index);
    }

    public <E extends T> void addAll(final Collection<E> entries) {
        final int index = rows.size();
        rows.addAll(entries);
        fireTableRowsInserted(index, rows.size() - 1);
    }

    public void clear() {
        rows.clear();
        fireTableDataChanged();
    }

    protected <E, C extends Collection<E>> C fillColumnIntoCollection(final C collection, final int columnIndex) {
        for (int rowIndex = 0, size = rows.size(); rowIndex < size; rowIndex++) {
            final E e = getCellValue(rows.get(rowIndex), rowIndex, columnIndex);
            collection.add(e);
        }
        return collection;
    }

    protected <E> E getCellValue(final T object, final int rowIndex, final int columnIndex) {
        @SuppressWarnings("unchecked")
        final E e = (E) columns.get(columnIndex).getValue(object, rowIndex);
        return e;
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        return columns.get(columnIndex).getType();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public String getColumnName(final int columnIndex) {
        return columns.get(columnIndex).getName();
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    public int getRowForObject(final T object) {
        return rows.indexOf(object);
    }

    public List<T> getRows() {
        return unmodifiableRows;
    }

    public T getRowValue(final int rowIndex) {
        return rows.get(rowIndex);
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        final T object = getRowValue(rowIndex);
        return getCellValue(object, rowIndex, columnIndex);
    }

    @Override
    public boolean isCellEditable(final int rowIndex, final int columnIndex) {
        final ColumnHelper<T> helper = columns.get(columnIndex);
        final T rowValue = getRowValue(rowIndex);
        return helper.isEditable(rowValue, rowIndex);
    }

    @Override
    public Iterator<T> iterator() {
        return rows.iterator();
    }

    public void removeRow(final int rowIndex) {
        rows.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void rowUpdated(final T object) {
        final int row = getRowForObject(object);
        if (row != -1) {
            fireTableRowsUpdated(row, row);
        }
    }

    public void set(final Collection<T> entries) {
        this.rows.clear();
        this.rows.addAll(entries);
        fireTableDataChanged();
    }

    @Override
    public void setValueAt(final Object value, final int rowIndex, final int columnIndex) {
        final ColumnHelper<T> helper = columns.get(columnIndex);
        final T rowValue = getRowValue(rowIndex);
        helper.setValue(rowValue, value);
    }

    public void sortBy(final Comparator<T> comparator) {
        Collections.sort(rows, comparator);
        fireTableDataChanged();
    }

    public <E extends Comparable<E>> void sortByNaturalOrder() {
        @SuppressWarnings("unchecked")
        final List<E> list = (List<E>) rows;
        Collections.sort(list);
    }

}
