package com.example.data.snapshots.storage.repository;

import com.example.data.snapshots.storage.model.SnapshotRecord;
import org.springframework.data.repository.CrudRepository;

public interface SnapshotRecordRepository extends CrudRepository<SnapshotRecord, String> {

}
