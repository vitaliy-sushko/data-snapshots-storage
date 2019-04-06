package com.example.data.snapshots.storage.service.impl

import com.example.data.snapshots.storage.StorageRecordUtil
import com.example.data.snapshots.storage.UnitTest
import com.example.data.snapshots.storage.exception.RecordMappingException
import com.example.data.snapshots.storage.exception.ValidationException
import com.example.data.snapshots.storage.mapper.CsvRecordMapper
import com.example.data.snapshots.storage.model.SnapshotRecord
import com.example.data.snapshots.storage.repository.SnapshotRecordRepository
import com.example.data.snapshots.storage.service.SnapshotRecordProcessingManager
import com.fasterxml.jackson.core.JsonLocation
import com.fasterxml.jackson.core.JsonParseException
import org.junit.experimental.categories.Category
import spock.lang.Specification

import javax.validation.ConstraintViolation
import javax.validation.Validator
import java.time.LocalDateTime

@Category(UnitTest)
class SnapshotRecordProcessingManagerImplTest extends Specification {

  private CsvRecordMapper csvRecordMapper
  private SnapshotRecordRepository repository
  private Validator validator
  private SnapshotRecordProcessingManager manager

  def setup() {
    csvRecordMapper = Mock()
    repository = Mock()
    validator = Mock()
    manager = new SnapshotRecordProcessingManagerImpl(csvRecordMapper, repository, validator)
  }

  def "when manager is called with valid line to process it then repository is called to save record"() {
    given: "Valid record with line number"
    def line = StorageRecordUtil.getHeaderLine() + StorageRecordUtil.getValidRecordLine()
    def lineNumber = 2
    and: "Record that is mapped from given line"
    def snapshotRecord = new SnapshotRecord().tap {
      primaryKey = 'Zamit'
      name = 'South American sea lion'
      description = 'Leopard, indian'
      updatedTime = LocalDateTime.parse('2018-04-17T08:43:21.000')
    }

    when: "Manager is called with given record line"
    manager.processRecord(line, lineNumber)

    then: "Mapper is called to instantiate the record from the given line"
    1 * csvRecordMapper.mapRecord(line) >> snapshotRecord
    and: "Repository is called to save the record"
    1 * repository.save(snapshotRecord) >> snapshotRecord
  }

  def "when manager is called with primary key to get saved record then repository is called"() {
    given: "Existing record key"
    def key = "Zamit"
    and: "Existing record"
    def expectedRecord = StorageRecordUtil.prepareValidSnapshotRecord()

    when: "Manager is called with given key"
    def record = manager.getRecord(key)

    then: "Repository is called with the given key"
    1 * repository.findById(key) >> Optional.of(expectedRecord)
    and: "No exception is thrown"
    noExceptionThrown()
    and: "Returned record matches provided"
    record == expectedRecord
  }

  def "when manager is called with primary key to delete record then repository is called"() {
    given: "Existing record key"
    def key = "Zamit"

    when: "Manager is called with given key"
    manager.deleteRecord(key)

    then: "Repository is called with the given key"
    1 * repository.deleteById(key)
    and: "No exception is thrown"
    noExceptionThrown()
  }

  def "when mapped record is not valid Validation exception is thrown"() {
    given: "Invalid record line with line number"
    def line = StorageRecordUtil.getHeaderLine() + StorageRecordUtil.getInvalidRecordLine()
    def lineNumber = 4
    and: "Invalid record that is mapped from given line"
    def snapshotRecord = StorageRecordUtil.prepareInvalidSnapshotRecord()
    and: "Constrain violation that return message"
    def message = "'NAME' field value can not be empty or null"
    def constraintViolation = Mock(ConstraintViolation)
    constraintViolation.getMessage() >> message

    when: "Manager is called with given key"
    manager.processRecord(line, lineNumber)

    then: "Mapper called and return record"
    1 * csvRecordMapper.mapRecord(line) >> snapshotRecord
    then: "Validator is called and returned set of violations"
    1 * validator.validate(snapshotRecord) >> [constraintViolation]
    then:
    def exception = thrown(ValidationException)
    exception.message == message
    exception.lineNumber == lineNumber
  }

  def "when mapper fails to map record line RecordMappingException is thrown"() {
    given: "Line number"
    def lineNumber = 4

    when: "Manager is called with given key"
    manager.processRecord("", lineNumber)

    then: "Mapper called and return record"
    1 * csvRecordMapper.mapRecord(_) >> { throw new JsonParseException(null, Mock(JsonLocation)) }
    then:
    def exception = thrown(RecordMappingException)
    exception.message.find("Snapshot record mapping failed with exception:") != null
    exception.lineNumber == lineNumber
  }

}
