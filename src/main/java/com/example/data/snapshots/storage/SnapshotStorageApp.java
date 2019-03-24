package com.example.data.snapshots.storage;

import com.example.data.snapshots.storage.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootConfiguration
@EnableAutoConfiguration
@Import(ApplicationConfig.class)
public class SnapshotStorageApp {

    public static void main(String[] args) {
        SpringApplication.run(SnapshotStorageApp.class, args);
    }
}
