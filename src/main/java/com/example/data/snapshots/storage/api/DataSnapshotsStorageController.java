package com.example.data.snapshots.storage.api;

import com.example.data.snapshots.storage.model.SnapshotRecord;
import com.example.data.snapshots.storage.service.SnapshotProcessingOrchestrator;
import javax.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

@Controller
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

  @GetMapping(path = "/records/{id}", produces = {
      MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(code = HttpStatus.OK)
  public @ResponseBody
  SnapshotRecord get(@PathVariable("id") String primaryKey) {
    return snapshotProcessingOrchestrator.getRecord(primaryKey);
  }

  @DeleteMapping(path = "/records/{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") String primaryKey) {
    snapshotProcessingOrchestrator.deleteRecord(primaryKey);
  }

}
