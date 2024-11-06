package com.example.demo.util;

import java.util.ArrayList;
import java.util.List;

public class ExcelValidator {
    public List<ErrorData> validateRow(UserData data, int rowIndex) {
        List<ErrorData> errors = new ArrayList<>();

        if (data.getName() == null || data.getName().isEmpty()) {
            errors.add(new ErrorData(String.valueOf(rowIndex), "Name không được bỏ trống"));
        }
        if (data.getAddress() == null || data.getAddress().isEmpty()) {
            errors.add(new ErrorData(String.valueOf(rowIndex), "Address không được bỏ trống"));
        }
        return errors;
    }
}
