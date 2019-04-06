package com.example.data.snapshots.storage.model;

import java.io.Serializable;

public interface ProcessingResult extends Serializable {

  class ProcessingSuccess implements ProcessingResult {

    private static final long serialVersionUID = 292266722232707479L;

    public ProcessingSuccess(SnapshotRecord ignored) {
    }

  }

  class ProcessingFailure implements ProcessingResult {

    private static final long serialVersionUID = -2406528937073452581L;

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
