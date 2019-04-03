package com.example.data.snapshots.storage.mapper.impl

import com.example.data.snapshots.storage.StorageRecordUtil
import com.example.data.snapshots.storage.config.ApplicationConfig
import com.example.data.snapshots.storage.mapper.CsvRecordMapper
import com.fasterxml.jackson.core.JsonLocation
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.ObjectReader
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import spock.lang.Specification

import java.lang.reflect.Field
import java.time.LocalDateTime

class CsvRecordMapperImplTest extends Specification {

  CsvMapper csvMapper
  CsvRecordMapper recordMapper

  def setup() {
    csvMapper = buildMapper()
    recordMapper = new CsvRecordMapperImpl(csvMapper)
  }

  def "valid record line can be processed and return mapped record"() {
    given: "valid record line"
    def line = StorageRecordUtil.getHeaderLine() +
        'Zamit,South American sea lion,"Leopard, indian",2018-04-17T08:43:21.000'

    when:
    def snapshotRecord = recordMapper.mapRecord(line)

    then:
    snapshotRecord != null
    snapshotRecord.primaryKey == 'Zamit'
    snapshotRecord.name == 'South American sea lion'
    snapshotRecord.description == 'Leopard, indian'
    snapshotRecord.updatedTime == LocalDateTime.parse('2018-04-17T08:43:21.000')
  }

  def "attempt to map header only line produces MismatchedInputException"() {
    given: "invalid record line"
    def line = StorageRecordUtil.getHeaderLine()

    when:
    def snapshotRecord = recordMapper.mapRecord(line)

    then:
    thrown(MismatchedInputException)
    snapshotRecord == null
  }

  def "exception thrown by mapper get re-thrown"() {
    given: "Mapper that just throws exception whenever asked to map a record"
    def objectReader = Mock(ObjectReader)
    objectReader.readValue(_) >> { throw new JsonParseException(null, Mock(JsonLocation)) }
    Field field = CsvRecordMapperImpl.class.getDeclaredField("reader")
    field.setAccessible(true)
    field.set(recordMapper, objectReader)

    when:
    def snapshotRecord = recordMapper.mapRecord("")

    then:
    thrown(JsonParseException)
    snapshotRecord == null
  }

  private CsvMapper buildMapper() {
    def config = new ApplicationConfig()
    config.@dateTimeFormat = 'yyyy-MM-dd\'T\'HH:mm:ss.SSS'
    csvMapper = config.csvMapper()
    csvMapper
  }
}
