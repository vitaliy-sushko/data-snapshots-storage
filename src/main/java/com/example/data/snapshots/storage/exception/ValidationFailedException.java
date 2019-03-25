package com.example.data.snapshots.storage.exception;

public class ValidationFailedException extends RuntimeException {

  public ValidationFailedException(String message) {
    super(message);
  }
}
