package com.balantes.property_management_system.config;

public enum USER_TYPE {
    GENERAL("GENERAL"),

    ADMIN("ADMIN");

    private String type;

    USER_TYPE(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}