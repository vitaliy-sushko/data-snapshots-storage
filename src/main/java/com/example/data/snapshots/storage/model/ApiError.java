package com.example.data.snapshots.storage.model;

import com.example.data.snapshots.storage.model.ProcessingResult.ProcessingFailure;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;

public final class ApiError {

  @JsonProperty
  private final String message;

  @JsonProperty
  @JsonInclude(Include.NON_NULL)
  private List<ProcessingFailure> errors;

  public ApiError(String message) {
    this.message = message;
  }

  public ApiError(String message, List<ProcessingFailure> errors) {
    this.message = message;
    this.errors = errors;
  }

  public String getMessage() {
    return message;
  }

  public List<ProcessingFailure> getErrors() {
    return errors;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiError apiError = (ApiError) o;
    return Objects.equals(message, apiError.message) &&
        Objects.equals(errors, apiError.errors);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, errors);
  }

  @Override
  public String toString() {
    return "ApiError{" +
        "message='" + message + '\'' +
        ", errors=" + errors +
        '}';
  }
}
