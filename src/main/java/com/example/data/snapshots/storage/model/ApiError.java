package com.example.data.snapshots.storage.model;

import java.util.Objects;

public final class ApiError {
  private final String message;

  public ApiError(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
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
    return Objects.equals(message, apiError.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message);
  }

  @Override
  public String toString() {
    return "ApiError{" +
        "message='" + message + '\'' +
        '}';
  }
}
