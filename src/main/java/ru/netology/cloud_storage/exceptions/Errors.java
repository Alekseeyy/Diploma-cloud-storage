package ru.netology.cloud_storage.exceptions;

public enum Errors {
    // security error
    UNAUTHORIZED_REQUEST("Unauthorized request "),

    // data init error
    COULD_NOT_CREATE_USER_DIRECTORIES("Could not create user directories ");

    private final String description;

    Errors(String description) {
        this.description = description;
    }

    public String value() {
        return description;
    }
}
