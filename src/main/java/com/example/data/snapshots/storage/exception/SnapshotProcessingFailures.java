package com.example.data.snapshots.storage.exception;

import com.example.data.snapshots.storage.model.ProcessingResult.ProcessingFailure;
import java.util.List;

public class SnapshotProcessingFailures extends RuntimeException {

  private final List<ProcessingFailure> processingFailures;

  public SnapshotProcessingFailures(String message, List<ProcessingFailure> processingFailures) {
    super(message);
    this.processingFailures = processingFailures;
  }

  public List<ProcessingFailure> getProcessingFailures() {
    return processingFailures;
  }
}
