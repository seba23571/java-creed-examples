package com.javacreed.examples.swing;

public class SampleData {

    private long id;
    private String name;
    private String surname;

    public SampleData() {}

    public SampleData(final long id, final String name, final String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

}
