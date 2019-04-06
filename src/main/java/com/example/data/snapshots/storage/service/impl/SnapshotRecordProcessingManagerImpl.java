package com.example.data.snapshots.storage.service.impl;

import com.example.data.snapshots.storage.exception.RecordMappingException;
import com.example.data.snapshots.storage.exception.RecordNotFoundException;
import com.example.data.snapshots.storage.exception.ValidationException;
import com.example.data.snapshots.storage.mapper.CsvRecordMapper;
import com.example.data.snapshots.storage.model.SnapshotRecord;
import com.example.data.snapshots.storage.repository.SnapshotRecordRepository;
import com.example.data.snapshots.storage.service.SnapshotRecordProcessingManager;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SnapshotRecordProcessingManagerImpl implements SnapshotRecordProcessingManager {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(SnapshotRecordProcessingManagerImpl.class);

  private final CsvRecordMapper csvRecordMapper;
  private final SnapshotRecordRepository repository;
  private final Validator validator;

  public SnapshotRecordProcessingManagerImpl(
      CsvRecordMapper csvRecordMapper,
      SnapshotRecordRepository repository,
      Validator validator) {
    this.csvRecordMapper = csvRecordMapper;
    this.repository = repository;
    this.validator = validator;
  }

  @Override
  public SnapshotRecord processRecord(String line, int lineNumber) {
    SnapshotRecord snapshotRecord = mapRecord(line, lineNumber);
    Set<ConstraintViolation<SnapshotRecord>> violations = validator.validate(snapshotRecord);
    if (violations != null && !violations.isEmpty()) {
      throw new ValidationException(violations.stream()
          .map(ConstraintViolation::getMessage)
          .collect(Collectors.joining("; ")), lineNumber);
    }

    return repository.save(snapshotRecord);
  }

  @Override
  public SnapshotRecord getRecord(String primaryKey) {
    return repository.findById(primaryKey)
        .orElseThrow(() -> new RecordNotFoundException(primaryKey));
  }

  @Override
  public void deleteRecord(String primaryKey) {
    repository.deleteById(primaryKey);
  }

  private SnapshotRecord mapRecord(String line, int lineNumber) {
    try {
      return csvRecordMapper.mapRecord(line);
    } catch (IOException e) {
      String message = String.format(
          "Snapshot record mapping failed with exception: %s", e.getMessage());
      LOGGER.debug(message, e);
      throw new RecordMappingException(message, lineNumber);
    }
  }

}
