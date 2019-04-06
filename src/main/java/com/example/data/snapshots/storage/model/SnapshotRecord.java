package com.example.data.snapshots.storage.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("SnapshotRecord")
@JsonPropertyOrder(value = {"primaryKey", "name", "description", "updatedTimestamp"})
public final class SnapshotRecord implements Serializable {

  private static final long serialVersionUID = -6683760972076985028L;

  @Id
  @NotEmpty(message = "{primary.key.can.not.be.empty.or.null}")
  @JsonProperty("primaryKey")
  @JsonAlias("PRIMARY_KEY")
  private String primaryKey;

  @NotEmpty(message = "{name.can.not.be.empty.or.null}")
  @JsonProperty("name")
  @JsonAlias("NAME")
  private String name;

  @NotEmpty(message = "{description.can.not.be.empty.or.null}")
  @JsonProperty("description")
  @JsonAlias("DESCRIPTION")
  private String description;

  @NotNull(message = "{updated.timestamp.can.not.be.null}")
  @JsonProperty("updatedTimestamp")
  @JsonAlias("UPDATED_TIMESTAMP")
  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
  private LocalDateTime updatedTime;

  public String getPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(String primaryKey) {
    this.primaryKey = primaryKey;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDateTime getUpdatedTime() {
    return updatedTime;
  }

  public void setUpdatedTime(LocalDateTime updatedTime) {
    this.updatedTime = updatedTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SnapshotRecord that = (SnapshotRecord) o;
    return Objects.equals(primaryKey, that.primaryKey) && Objects.equals(name, that.name) && Objects
        .equals(description, that.description) && Objects.equals(updatedTime, that.updatedTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(primaryKey, name, description, updatedTime);
  }

  @Override
  public String toString() {
    return "SnapshotRecord{" +
        "primaryKey='" + primaryKey + '\'' +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", updatedTime=" + updatedTime +
        '}';
  }
}
