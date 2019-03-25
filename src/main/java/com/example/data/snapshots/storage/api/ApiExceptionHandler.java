package com.example.data.snapshots.storage.api;

import com.example.data.snapshots.storage.exception.FailedSnapshotProcessing;
import com.example.data.snapshots.storage.exception.RecordNotFound;
import com.example.data.snapshots.storage.exception.ValidationFailedException;
import com.example.data.snapshots.storage.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(RecordNotFound.class)
  protected ResponseEntity<?> handleEntityNotFound(RecordNotFound ex) {
    ApiError apiError = new ApiError(String.format(
        "Record with primaryKey [%s] was not found ", ex.getPrimaryKey()));
    return new ResponseEntity(apiError, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ValidationFailedException.class)
  protected ResponseEntity<?> handleFailedProcessing(ValidationFailedException ex) {
    ApiError apiError = new ApiError(ex.getMessage());
    return new ResponseEntity(apiError, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(FailedSnapshotProcessing.class)
  protected ResponseEntity<?> handleFailedProcessing(FailedSnapshotProcessing ex) {
    ApiError apiError = new ApiError(ex.getMessage());
    return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
