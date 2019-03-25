package com.example.data.snapshots.storage.config;

import com.example.data.snapshots.storage.mapper.CsvRecordMapper;
import com.example.data.snapshots.storage.mapper.impl.CsvRecordMapperImpl;
import com.example.data.snapshots.storage.repository.SnapshotRecordRepository;
import com.example.data.snapshots.storage.service.SnapshotProcessingOrchestrator;
import com.example.data.snapshots.storage.service.SnapshotRecordProcessingManager;
import com.example.data.snapshots.storage.service.impl.SnapshotProcessingOrchestratorImpl;
import com.example.data.snapshots.storage.service.impl.SnapshotRecordProcessingManagerImpl;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import javax.validation.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.AsyncTaskExecutor;

@Configuration
@Import({ThreadPoolConfig.class, RedisConfig.class})
public class ServiceConfig {

  @Bean
  public CsvRecordMapper csvRecordMapper(CsvMapper csvMapper) {
    return new CsvRecordMapperImpl(csvMapper);
  }

  @Bean
  public SnapshotRecordProcessingManager snapshotRecordProcessingManager(
      CsvRecordMapper csvRecordMapper,
      SnapshotRecordRepository repository,
      Validator validator) {
    return new SnapshotRecordProcessingManagerImpl(csvRecordMapper, repository, validator);
  }

  @Bean
  public SnapshotProcessingOrchestrator snapshotRecordProcessingOrchestrator(
      SnapshotRecordProcessingManager manager,
      AsyncTaskExecutor taskExecutor) {
    return new SnapshotProcessingOrchestratorImpl(manager, taskExecutor);
  }

}
