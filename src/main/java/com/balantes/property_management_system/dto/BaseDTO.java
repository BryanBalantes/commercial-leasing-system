package com.balantes.property_management_system.dto;

import java.util.ArrayList;
import java.util.List;

public class BaseDTO {

    private List<String> errors = new ArrayList<>();

    public void addError(String error) {
        errors.add(error);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }
}