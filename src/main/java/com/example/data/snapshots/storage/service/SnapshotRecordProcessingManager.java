package com.example.data.snapshots.storage.service;

import com.example.data.snapshots.storage.model.SnapshotRecord;

public interface SnapshotRecordProcessingManager {

  SnapshotRecord processRecord(String line, int lineNumber);

  SnapshotRecord getRecord(String primaryKey);

  void deleteRecord(String primaryKey);

}
