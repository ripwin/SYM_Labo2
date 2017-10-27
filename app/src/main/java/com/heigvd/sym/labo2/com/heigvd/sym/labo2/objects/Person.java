package com.heigvd.sym.labo2.com.heigvd.sym.labo2.objects;

import java.util.Arrays;

public class Person {
    private final String name;
    private final String firstName;
    private String middleName;
    private final String gender;
    private final Phone[] phones;

    public Person(String name, String firstName, String gender, Phone ... phones) {
        this.name = name;
        this.firstName = firstName;
        this.gender = gender;
        this.middleName = new String();

        if(phones.length <= 0) {
            throw new IllegalArgumentException("Must be at least 1 phone number");
        }

        this.phones = Arrays.copyOf(phones, phones.length);
    }

    public Person(String name, String firstName, String middleName, String gender, Phone ... phones) {
        this(name, firstName, gender, phones);
        this.middleName = middleName;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getGender() {
        return gender;
    }

    public Phone[] getPhones() {
        return phones;
    }
}
