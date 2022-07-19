package com.project.telephonedirectory;

import java.io.Serializable;

public class Persons implements Serializable {
    private int person_id;
    private String person_name;
    private String phone_number;
    private String person_email;

    public Persons() {
    }

    public Persons(int person_id, String person_name, String phone_number, String person_email) {
        this.person_id = person_id;
        this.person_name = person_name;
        this.phone_number = phone_number;
        this.person_email = person_email;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPerson_email() {
        return person_email;
    }

    public void setPerson_email(String person_email) {
        this.person_email = person_email;
    }
}
