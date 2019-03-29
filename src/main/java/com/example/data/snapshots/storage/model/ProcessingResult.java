package com.example.data.snapshots.storage.model;

public interface ProcessingResult {

  class ProcessingSuccess implements ProcessingResult {

    private final SnapshotRecord snapshotRecord;

    public ProcessingSuccess(SnapshotRecord snapshotRecord) {
      this.snapshotRecord = snapshotRecord;
    }

  }

  class ProcessingFailure implements ProcessingResult {
    private final int lineNumber;
    private final String message;

    public ProcessingFailure(int lineNumber, String message) {
      this.lineNumber = lineNumber;
      this.message = message;
    }

    public int getLineNumber() {
      return lineNumber;
    }

    public String getMessage() {
      return message;
    }
  }
}
