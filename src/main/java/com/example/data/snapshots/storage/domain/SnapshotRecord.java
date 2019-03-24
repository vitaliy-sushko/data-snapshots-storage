package com.example.data.snapshots.storage.domain;

import org.joda.time.DateTime;

import java.io.Serializable;

public final class SnapshotRecord implements Serializable {

    private static final long serialVersionUID = -6683760972076985028L;

    private final String primaryKey;
    private final String name;
    private final String description;
    private final DateTime updatedTime;

    private SnapshotRecord(SnapshotRecordBuilder builder) {
        this.primaryKey = builder.primaryKey;
        this.name = builder.name;
        this.description = builder.description;
        this.updatedTime = builder.updatedTime;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public DateTime getUpdatedTime() {
        return updatedTime;
    }

    public static SnapshotRecordBuilder builder() {
        return new SnapshotRecordBuilder();
    }

    public static class SnapshotRecordBuilder {
        private String primaryKey;
        private String name;
        private String description;
        private DateTime updatedTime;

        public SnapshotRecordBuilder primaryKey(String primaryKey) {
            this.primaryKey = primaryKey;
            return this;
        }

        public SnapshotRecordBuilder name(String name) {
            this.name = name;
            return this;
        }

        public SnapshotRecordBuilder description(String description) {
            this.description = description;
            return this;
        }

        public SnapshotRecordBuilder updatedTime(DateTime updatedTime) {
            this.updatedTime = updatedTime;
            return this;
        }

        public SnapshotRecord build() {
            return new SnapshotRecord(this);
        }
    }
}
