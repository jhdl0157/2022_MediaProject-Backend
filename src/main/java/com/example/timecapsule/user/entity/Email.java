package com.example.timecapsule.user.entity;

import java.util.Objects;

public class Email {
    private String email;

    public Email(String email) {
        verify(email);
        this.email = email;
    }

    private void verify(String email) {
        //email 법칙에 맞게
        if () {
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return Objects.equals(email, email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
