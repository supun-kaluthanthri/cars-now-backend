package com.cars_now.backend.utils;

public enum ControllerAttributes {
    X_TOTAL_COUNT("X-Total-Count"),
    ACCESS_CONTROL_HEADERS("Access-Control-Expose-Headers");


    private final String attrib;

    ControllerAttributes(final String attrib) {
        this.attrib = attrib;
    }

    public String tag() {
        return attrib;
    }
}

