package com.example.data.snapshots.storage.exception;

public abstract class AbstractRecordException extends RuntimeException {

  private final int lineNumber;

  AbstractRecordException(String message, int lineNumber) {
    super(message);
    this.lineNumber = lineNumber;
  }

  public int getLineNumber() {
    return lineNumber;
  }
}
