package com.heigvd.sym.labo2.com.heigvd.sym.labo2.objects;

public class Phone {

    public enum Type {
        Home("home"),
        Work("work"),
        Mobile("mobile");

        private String name;

        private Type(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private final String number;
    private final Type type;

    public Phone(String number, Phone.Type type) {
        this.number = number;
        this.type = type;
    }

    public String number() {
        return number;
    }

    public String type() {
        return type.toString();
    }
}
