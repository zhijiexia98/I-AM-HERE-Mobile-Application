package edu.northestern.cs5520_teamproject_iamhere.IamHere;

import java.util.Objects;

public class Contacts {
    protected String Name;
    protected String phoneNumber;

    public Contacts() {}

    public Contacts(String name, String phoneNumber) {
        Name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contacts contacts = (Contacts) o;
        return Objects.equals(Name, contacts.Name) && Objects.equals(phoneNumber, contacts.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Name, phoneNumber);
    }

    @Override
    public String toString() {
        return "Contacts{" +
                "Name='" + Name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
