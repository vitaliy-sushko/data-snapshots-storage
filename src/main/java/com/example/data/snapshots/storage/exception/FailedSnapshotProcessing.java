package com.example.data.snapshots.storage.exception;

public class FailedSnapshotProcessing extends RuntimeException {

  public FailedSnapshotProcessing(String message) {
    super(message);
  }

  public FailedSnapshotProcessing(String message, Throwable cause) {
    super(message, cause);
  }
}
