package com.heigvd.sym.labo2.com.heigvd.sym.labo2.objects;

import java.util.Arrays;

public class Person {
    private final String name;
    private final String firstName;
    private String middleName;
    private final Gender gender;
    private final Phone[] phones;

    public enum Gender {
        Male("Male"),
        Female("Female");

        private final String gender;

        private Gender(String gender) {
            this.gender = gender;
        }

        @Override
        public String toString() {
            return gender;
        }
    }

    public Person(String name, String firstName, Gender gender, Phone ... phones) {
        this.name = name;
        this.firstName = firstName;
        this.gender = gender;
        this.middleName = new String();

        if(phones.length <= 0) {
            throw new IllegalArgumentException("Must be at least 1 phone number");
        }

        this.phones = Arrays.copyOf(phones, phones.length);
    }

    public Person(String name, String firstName, String middleName, Gender gender, Phone ... phones) {
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
        return gender.toString();
    }

    public Phone[] getPhones() {
        return phones;
    }
}
