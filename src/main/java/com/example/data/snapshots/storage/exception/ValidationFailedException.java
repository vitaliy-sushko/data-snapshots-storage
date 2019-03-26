package com.example.data.snapshots.storage.exception;

import com.example.data.snapshots.storage.model.ProcessingResult.ProcessingFailure;
import java.util.List;

public class ValidationFailedException extends RuntimeException {

  private final List<ProcessingFailure> errors;

  public ValidationFailedException(String message, List<ProcessingFailure> errors) {
    super(message);
    this.errors = errors;
  }

  public List<ProcessingFailure> getErrors() {
    return errors;
  }
}
