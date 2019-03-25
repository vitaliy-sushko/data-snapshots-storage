package com.example.data.snapshots.storage.model;

import java.util.Collection;
import javax.validation.ConstraintViolation;

public interface ProcessingResult {

  class ProcessingSuccess implements ProcessingResult {

  }


  class ProcessingFailure implements ProcessingResult {
    private final String message;
    private final SnapshotRecord snapshotRecord;
    private final int lineNumber;

    public ProcessingFailure(
        String message, SnapshotRecord snapshotRecord, int lineNumber) {
      this.message = message;
      this.snapshotRecord = snapshotRecord;
      this.lineNumber = lineNumber;
    }

    public String getMessage() {
      return message;
    }

    public SnapshotRecord getSnapshotRecord() {
      return snapshotRecord;
    }

    public int getLineNumber() {
      return lineNumber;
    }
  }
}
