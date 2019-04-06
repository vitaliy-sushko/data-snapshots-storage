package com.example.data.snapshots.storage.api;

import com.example.data.snapshots.storage.exception.RecordNotFoundException;
import com.example.data.snapshots.storage.exception.SnapshotProcessingFailures;
import com.example.data.snapshots.storage.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(RecordNotFoundException.class)
  protected ResponseEntity handleEntityNotFound(RecordNotFoundException ex) {
    ApiError apiError = new ApiError(String.format(
        "Record with primaryKey [%s] was not found", ex.getPrimaryKey()));
    return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(SnapshotProcessingFailures.class)
  protected ResponseEntity handleProcessingFailures(SnapshotProcessingFailures ex) {
    ApiError apiError = new ApiError(ex.getMessage(), ex.getProcessingFailures());
    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
  }

}
