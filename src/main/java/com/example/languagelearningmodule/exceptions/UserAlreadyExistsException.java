package com.example.languagelearningmodule.exceptions;

public final class UserAlreadyExistsException extends RuntimeException {
        public UserAlreadyExistsException() {
            super();
        }

        public UserAlreadyExistsException(final String message) {
            super(message);
        }

}
