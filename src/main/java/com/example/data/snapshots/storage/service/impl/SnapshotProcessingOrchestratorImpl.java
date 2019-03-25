package com.example.data.snapshots.storage.service.impl;

import com.example.data.snapshots.storage.exception.FailedSnapshotProcessing;
import com.example.data.snapshots.storage.exception.ValidationFailedException;
import com.example.data.snapshots.storage.model.ProcessingResult;
import com.example.data.snapshots.storage.model.ProcessingResult.ProcessingFailure;
import com.example.data.snapshots.storage.model.SnapshotRecord;
import com.example.data.snapshots.storage.service.SnapshotProcessingOrchestrator;
import com.example.data.snapshots.storage.service.SnapshotRecordProcessingManager;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.multipart.MultipartFile;

public class SnapshotProcessingOrchestratorImpl implements SnapshotProcessingOrchestrator {

  private static final Logger LOGGER = LoggerFactory.getLogger(SnapshotProcessingOrchestratorImpl.class);

  private final SnapshotRecordProcessingManager manager;
  private final AsyncTaskExecutor executor;

  public SnapshotProcessingOrchestratorImpl(
      SnapshotRecordProcessingManager manager,
      AsyncTaskExecutor executor) {
    this.manager = manager;
    this.executor = executor;
  }

  @Override
  public void processSnapshot(MultipartFile dataSnapshot) {
    AtomicInteger lineNumber = new AtomicInteger(0);
    try (InputStream inputStream = new BufferedInputStream(dataSnapshot.getInputStream());
        Reader reader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(reader)) {
      final String header = br.readLine();
      List<String> processingFailures = br.lines().skip(1L).map(line ->
          CompletableFuture.supplyAsync(() ->
              manager.processRecord(header + "\n" + line, lineNumber.incrementAndGet()), executor))
          .map(this::getProcessingReulst)
          .filter(Objects::nonNull)
          .filter(pr -> pr instanceof ProcessingFailure)
          .map(ProcessingFailure.class::cast)
          .map(failure -> String.format(
              "Processing failed for line %d, with error(s): %s, %s",
              failure.getLineNumber(),
              failure.getMessage(),
              String.valueOf(failure.getSnapshotRecord())))
          .collect(Collectors.toList());

      if (!processingFailures.isEmpty()) {
        throw new ValidationFailedException(String.join("; ", processingFailures));
      }

    } catch (IOException e) {
      throw new FailedSnapshotProcessing("Snapshot processing failed during file read", e);
    }
  }

  private ProcessingResult getProcessingReulst(CompletableFuture<ProcessingResult> cf) {
    try {
      return cf.get();
    } catch (InterruptedException | ExecutionException e) {
      LOGGER.error(e.getMessage());
    }
    return null;
  }

  @Override
  public SnapshotRecord getRecord(String primaryKey) {
    return manager.getRecord(primaryKey);
  }

  @Override
  public void deleteRecord(String primaryKey) {
    manager.deleteRecord(primaryKey);
  }
}
