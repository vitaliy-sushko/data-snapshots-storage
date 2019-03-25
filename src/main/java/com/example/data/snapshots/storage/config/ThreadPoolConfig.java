package com.example.data.snapshots.storage.config;

import com.example.data.snapshots.storage.config.properties.ThreadPoolExecutorProperties;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolConfig {

  @Bean
  @ConfigurationProperties(prefix = "data.snapshot.storage.task.execution.pool")
  public ThreadPoolExecutorProperties threadPoolExecutorProperties() {
    return new ThreadPoolExecutorProperties();
  }

  @Bean
  public AsyncTaskExecutor asyncTaskExecutor(
      ThreadPoolExecutorProperties properties) {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(properties.getCorePoolSize());
    executor.setMaxPoolSize(properties.getMaxPoolSize());
    executor.setQueueCapacity(properties.getQueueCapacity());
    executor.setThreadNamePrefix("default_executor");
    executor.setRejectedExecutionHandler(new CallerRunsPolicy());
    executor.initialize();
    return executor;
  }

}
