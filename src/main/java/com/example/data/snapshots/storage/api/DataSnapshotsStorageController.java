package com.example.data.snapshots.storage.api;

import com.example.data.snapshots.storage.domain.SnapshotRecord;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(path = "/data/snapshot/")
public class DataSnapshotsStorageController {

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public void upload(@RequestParam("snapshot") MultipartFile file) {

    }

    @GetMapping(path = "/records/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody SnapshotRecord get(@PathVariable("id") String primaryKey) {
        return SnapshotRecord.builder()
                .primaryKey(primaryKey)
                .name("")
                .description("")
                .updatedTime(DateTime.now())
                .build();
    }

    @DeleteMapping(path = "/records/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String primaryKey) {

    }

}
