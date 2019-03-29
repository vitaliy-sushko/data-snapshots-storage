package com.example.data.snapshots.storage.exception;

public class ValidationException extends AbstractRecordException {

  public ValidationException(String message, int lineNumber) {
    super(message, lineNumber);
  }

}
