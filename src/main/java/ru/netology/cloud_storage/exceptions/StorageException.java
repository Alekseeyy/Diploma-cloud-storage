package ru.netology.cloud_storage.exceptions;

import org.springframework.security.core.AuthenticationException;

public class StorageException extends AuthenticationException {

    public StorageException(String message) {
        super(message);
    }
}
