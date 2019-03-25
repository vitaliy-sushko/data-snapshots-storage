package com.example.data.snapshots.storage.service;

import com.example.data.snapshots.storage.model.ProcessingResult;
import com.example.data.snapshots.storage.model.SnapshotRecord;

public interface SnapshotRecordProcessingManager {

  ProcessingResult processRecord(String line, int lineNumber);

  SnapshotRecord getRecord(String primaryKey);

  void deleteRecord(String primaryKey);

}
