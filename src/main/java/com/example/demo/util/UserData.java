package com.example.demo.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserData {
    private String name;
    private String address;

    // Constructor mặc định (không tham số)
    public UserData() {}

    // Constructor có tham số
    public UserData(String name, String address) {
        this.name = name;
        this.address = address;
    }

    // Getters và Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
