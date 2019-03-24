package com.example.data.snapshots.storage.config;

import com.example.data.snapshots.storage.api.DataSnapshotsStorageController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
public class WebConfig {

    @Bean
    public DataSnapshotsStorageController dataSnapshotsStorageController() {
        return new DataSnapshotsStorageController();
    }
}
