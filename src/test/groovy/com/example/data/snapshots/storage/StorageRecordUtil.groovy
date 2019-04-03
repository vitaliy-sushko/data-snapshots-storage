package com.example.data.snapshots.storage

import com.example.data.snapshots.storage.model.SnapshotRecord

import java.time.LocalDateTime;

class StorageRecordUtil {

  static String getHeaderLine() {
    'PRIMARY_KEY,NAME,DESCRIPTION,UPDATED_TIMESTAMP' + "\n"
  }

  static String getValidRecordLine() {
    'Zamit,South American sea lion,"Leopard, indian",2018-04-17T08:43:21.000'
  }

  static String getInvalidRecordLine() {
    'Zamit,,"Leopard, indian",2018-04-17T08:43:21.000'
  }

  static SnapshotRecord prepareValidSnapshotRecord() {
    new SnapshotRecord().tap {
      primaryKey = 'Zamit'
      name = 'South American sea lion'
      description = 'Leopard, indian'
      updatedTime = LocalDateTime.parse('2018-04-17T08:43:21.000')
    }
  }

  static SnapshotRecord prepareInvalidSnapshotRecord() {
    new SnapshotRecord().tap {
      primaryKey = 'Zamit'
      description = 'Leopard, indian'
      updatedTime = LocalDateTime.parse('2018-04-17T08:43:21.000')
    }
  }

}
