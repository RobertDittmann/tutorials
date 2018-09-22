package com.dittmann.marklogic.client.example;

public class TestClass {

    private final String name;
    private final String surname;


    TestClass(final String name, final String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public String toString() {
        return "TestClass{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}