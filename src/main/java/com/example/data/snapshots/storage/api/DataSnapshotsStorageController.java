package com.example.data.snapshots.storage.api;

import com.example.data.snapshots.storage.model.SnapshotRecord;
import com.example.data.snapshots.storage.service.SnapshotProcessingOrchestrator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/data/snapshot/")
public class DataSnapshotsStorageController {

  private final SnapshotProcessingOrchestrator snapshotProcessingOrchestrator;

  public DataSnapshotsStorageController(
      SnapshotProcessingOrchestrator snapshotProcessingOrchestrator) {
    this.snapshotProcessingOrchestrator = snapshotProcessingOrchestrator;
  }

  @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ResponseStatus(code = HttpStatus.OK)
  public void upload(@RequestParam("snapshot") @NotNull MultipartFile snapshot) {
    snapshotProcessingOrchestrator.processSnapshot(snapshot);
  }

  @GetMapping(path = "/records/{id}")
  @ResponseStatus(code = HttpStatus.OK)
  public @ResponseBody
  SnapshotRecord get(
      @NotEmpty(message = "Primary key can't be empty") @PathVariable("id") String primaryKey) {
    return snapshotProcessingOrchestrator.getRecord(primaryKey);
  }

  @DeleteMapping(path = "/records/{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void delete(
      @NotEmpty(message = "Primary key can't be empty") @PathVariable("id") String primaryKey) {
    snapshotProcessingOrchestrator.deleteRecord(primaryKey);
  }

}
