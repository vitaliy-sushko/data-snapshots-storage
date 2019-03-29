package com.example.data.snapshots.storage.exception;

public class RecordNotFoundException extends RuntimeException {

  private final String primaryKey;

  public RecordNotFoundException(String primaryKey) {
    this.primaryKey = primaryKey;
  }

  public String getPrimaryKey() {
    return primaryKey;
  }
}
