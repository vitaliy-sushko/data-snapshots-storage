package com.example.data.snapshots.storage.exception;

public class RecordNotFound extends RuntimeException {

  private final String primaryKey;

  public RecordNotFound(String primaryKey) {
    this.primaryKey = primaryKey;
  }

  public String getPrimaryKey() {
    return primaryKey;
  }
}
