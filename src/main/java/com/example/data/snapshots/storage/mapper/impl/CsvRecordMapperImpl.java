package com.example.data.snapshots.storage.mapper.impl;

import com.example.data.snapshots.storage.model.SnapshotRecord;
import com.example.data.snapshots.storage.mapper.CsvRecordMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.IOException;

public class CsvRecordMapperImpl implements CsvRecordMapper {

  private final ObjectReader reader;

  public CsvRecordMapperImpl(CsvMapper csvMapper) {
    CsvSchema schema = csvMapper.schemaFor(SnapshotRecord.class)
        .withHeader().withColumnReordering(false);
    reader = csvMapper.readerFor(SnapshotRecord.class).with(schema)
        .with(schema.withColumnSeparator(CsvSchema.DEFAULT_COLUMN_SEPARATOR));
  }

  @Override
  public SnapshotRecord mapRecord(String line) throws IOException {
    return reader.readValue(line);
  }

}
