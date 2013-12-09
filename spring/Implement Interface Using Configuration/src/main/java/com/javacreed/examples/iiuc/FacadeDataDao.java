package com.javacreed.examples.iiuc;

import java.util.ArrayList;
import java.util.List;

public class FacadeDataDao<T> implements DataDao<T> {

    private final List<DataDao<T>> list = new ArrayList<>();

    public FacadeDataDao(final List<DataDao<T>> list) {
        this.list.addAll(list);
    }

    @Override
    public void save(final T data) {
        for (final DataDao<T> dao : list) {
            dao.save(data);
        }
    }
}
