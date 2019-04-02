package com.example.data.snapshots.storage;

class StorageRecordUtil {

  static String getHeaderLine() {
    'PRIMARY_KEY,NAME,DESCRIPTION,UPDATED_TIMESTAMP' + "\n"
  }

  static String getRecordLine() {
    'Zamit,South American sea lion,"Leopard, indian",2018-04-17T08:43:21.000'
  }

}
