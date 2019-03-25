package com.example.data.snapshots.storage.service;

import com.example.data.snapshots.storage.model.SnapshotRecord;
import org.springframework.web.multipart.MultipartFile;

public interface SnapshotProcessingOrchestrator {

  void processSnapshot(MultipartFile dataSnapshot);

  SnapshotRecord getRecord(String primaryKey);

  void deleteRecord(String primaryKey);

}
