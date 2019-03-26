package com.example.data.snapshots.storage.model;

public interface ProcessingResult {

  class ProcessingSuccess implements ProcessingResult {

  }

  class ProcessingFailure implements ProcessingResult {
    private final String message;
    private final int lineNumber;

    public ProcessingFailure(String message, int lineNumber) {
      this.message = message;
      this.lineNumber = lineNumber;
    }

    public String getMessage() {
      return message;
    }

    public int getLineNumber() {
      return lineNumber;
    }
  }
}
