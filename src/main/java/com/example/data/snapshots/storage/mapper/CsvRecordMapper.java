package com.example.data.snapshots.storage.mapper;

import com.example.data.snapshots.storage.model.SnapshotRecord;
import java.io.IOException;

public interface CsvRecordMapper {

  SnapshotRecord mapRecord(String line) throws IOException;

}
