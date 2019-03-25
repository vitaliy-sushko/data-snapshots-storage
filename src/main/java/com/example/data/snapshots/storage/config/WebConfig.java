package com.example.data.snapshots.storage.config;

import com.example.data.snapshots.storage.api.ApiExceptionHandler;
import com.example.data.snapshots.storage.api.DataSnapshotsStorageController;
import com.example.data.snapshots.storage.service.SnapshotProcessingOrchestrator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
public class WebConfig {

  @Bean
  public ApiExceptionHandler exceptionHandler() {
    return new ApiExceptionHandler();
  }

  @Bean
  public DataSnapshotsStorageController dataSnapshotsStorageController(
      SnapshotProcessingOrchestrator snapshotRecordProcessingOrchestrator) {
    return new DataSnapshotsStorageController(snapshotRecordProcessingOrchestrator);
  }
}
