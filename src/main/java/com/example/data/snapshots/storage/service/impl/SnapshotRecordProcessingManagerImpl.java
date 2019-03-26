package com.example.data.snapshots.storage.service.impl;

import com.example.data.snapshots.storage.exception.RecordNotFound;
import com.example.data.snapshots.storage.mapper.CsvRecordMapper;
import com.example.data.snapshots.storage.model.ProcessingResult;
import com.example.data.snapshots.storage.model.ProcessingResult.ProcessingFailure;
import com.example.data.snapshots.storage.model.ProcessingResult.ProcessingSuccess;
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

  private static final Logger LOGGER = LoggerFactory.getLogger(SnapshotRecordProcessingManagerImpl.class);

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
  public ProcessingResult processRecord(String line, int lineNumber) {
    SnapshotRecord snapshotRecord = mapRecord(line);
    if (snapshotRecord != null) {
      Set<ConstraintViolation<SnapshotRecord>> constraintViolations =
          validator.validate(snapshotRecord);
      if (constraintViolations != null && !constraintViolations.isEmpty()) {
        return new ProcessingFailure(
            constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(";")), lineNumber);
      }

      repository.save(snapshotRecord);
    }

    return new ProcessingSuccess();
  }

  private SnapshotRecord mapRecord(String line) {
    try {
      return csvRecordMapper.mapRecord(line);
    } catch (IOException e) {
      LOGGER.error("Record mapping failed", e);
    }
    return null;
  }

  @Override
  public SnapshotRecord getRecord(String primaryKey) {
    return repository.findById(primaryKey).orElseThrow(() -> new RecordNotFound(primaryKey));
  }

  @Override
  public void deleteRecord(String primaryKey) {
    repository.deleteById(primaryKey);
  }
}
