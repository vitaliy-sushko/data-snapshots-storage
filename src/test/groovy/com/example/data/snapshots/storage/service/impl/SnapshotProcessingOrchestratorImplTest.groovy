package com.example.data.snapshots.storage.service.impl

import com.example.data.snapshots.storage.StorageRecordUtil
import com.example.data.snapshots.storage.UnitTest
import com.example.data.snapshots.storage.exception.RecordNotFoundException
import com.example.data.snapshots.storage.exception.SnapshotProcessingFailures
import com.example.data.snapshots.storage.exception.ValidationException
import com.example.data.snapshots.storage.model.SnapshotRecord
import com.example.data.snapshots.storage.service.SnapshotProcessingOrchestrator
import com.example.data.snapshots.storage.service.SnapshotRecordProcessingManager
import org.junit.experimental.categories.Category
import org.springframework.core.task.AsyncTaskExecutor
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import spock.lang.Specification

import java.nio.charset.Charset
import java.time.LocalDateTime

@Category(UnitTest)
class SnapshotProcessingOrchestratorImplTest extends Specification {

    private AsyncTaskExecutor executor
    private SnapshotProcessingOrchestrator orchestrator
    private SnapshotRecordProcessingManager manager

    def setup() {
        manager = Mock()
        executor = Mock()
        orchestrator = new SnapshotProcessingOrchestratorImpl(manager, executor)
    }

    def "sending non empty multipart file starts async processing of records"() {
        given: "File that contains header, data record, and empty string"
        def line = StorageRecordUtil.getHeaderLine() + StorageRecordUtil.getRecordLine()
        def file = prepareMockFile((line + "\n"))

        when: "Orchestrator is called to process this file"
        orchestrator.processSnapshot(file)

        then: "No exception is thrown"
        noExceptionThrown()
        and: "Manager called only once to process the only data record"
        1 * manager.processRecord(line, 2) >> new SnapshotRecord().tap {
            primaryKey = 'Zamit'
            name = 'South American sea lion'
            description = 'Leopard, indian'
            updatedTime = LocalDateTime.parse('2018-04-17T08:43:21.000')
        }
        and: "Executor have to run submitted runnable"
        1 * executor.execute(_) >> { Runnable task -> task.run() }
        and: "No more interactions expected with the manager"
        0 * manager._
        and: "No more interactions expected with the executor"
        0 * executor._
    }

    def "sending multipart file with header line does not start async processing of records neither result in exception"() {
        given: "File that contains header, data record, and empty string"
        def line = StorageRecordUtil.getHeaderLine()
        def file = prepareMockFile(line)

        when: "Orchestrator is called to process this file"
        orchestrator.processSnapshot(file)

        then: "No exception is thrown"
        noExceptionThrown()
        and: "No interactions expected with the manager"
        0 * manager._
        and: "No interactions expected with the executor"
        0 * executor._
    }

    def "sending empty multipart file does not start async processing of records neither results in exception"() {
        given: "File that contains header, data record, and empty string"
        def file = prepareMockFile("")

        when: "Orchestrator is called to process this file"
        orchestrator.processSnapshot(file)

        then: "No exception is thrown"
        noExceptionThrown()
        and: "No interactions expected with the manager"
        0 * manager._
        and: "No interactions expected with the executor"
        0 * executor._
    }

    def "sending null file produces NPE"() {
        given: "File that contains header, data record, and empty string"
        def file = null

        when: "Orchestrator is called to process this file"
        orchestrator.processSnapshot(file)

        then: "NPE is thrown"
        thrown(NullPointerException)
        and: "No interactions expected with the manager"
        0 * manager._
        and: "No interactions expected with the executor"
        0 * executor._
    }

    def "when manager throws exception then SnapshotProcessingFailures exception is thrown"() {
        given: "File that contains header, data record, and empty string"
        def line = StorageRecordUtil.getHeaderLine() + StorageRecordUtil.getRecordLine()
        def file = prepareMockFile((line + "\n"))

        when: "Orchestrator is called to process this file"
        orchestrator.processSnapshot(file)

        then: "Manager was called and threw ValidationException"
        1 * manager.processRecord(line, 2) >> { throw new ValidationException("Test message", 2) }
        and: "Executor have to run submitted runnable"
        1 * executor.execute(_) >> { Runnable task -> task.run() }
        and: "SnapshotProcessingFailures exception is thrown with non empty failures collection"
        def exception = thrown(SnapshotProcessingFailures)
        exception != null
        exception.processingFailures != null
        exception.processingFailures.size() == 1
    }

    def "when manager returns null result NPE is thrown with specific message"() {
        given: "File that contains header, data record, and empty string"
        def line = StorageRecordUtil.getHeaderLine() + StorageRecordUtil.getRecordLine()
        def file = prepareMockFile((line + "\n"))

        when: "Orchestrator is called to process this file"
        orchestrator.processSnapshot(file)

        then: "Manager was called and returned null"
        1 * manager.processRecord(line, 2)
        and: "Executor have to run submitted runnable"
        1 * executor.execute(_) >> { Runnable task -> task.run() }
        and: "SnapshotProcessingFailures is thrown with specific message"
        def exception = thrown(SnapshotProcessingFailures)
        exception?.processingFailures?.first()?.message == 'java.lang.NullPointerException: Save failed with unknown error'
    }

    def "when IOException is thrown during file read then SnapshotProcessingFailures is thrown"() {
        given: "Mock file that throws exception for getInputStream method invocation"
        def file = Mock(MockMultipartFile)
        file.getInputStream() >> { throw new IOException() }

        when: "Orchestrator is called to process this file"
        orchestrator.processSnapshot(file)

        then: "SnapshotProcessingFailures is thrown with specific message and line number"
        def exception = thrown(SnapshotProcessingFailures)
        exception?.message == 'Snapshot processing failed during file read'
        exception?.processingFailures?.first()?.lineNumber == -1
        exception?.processingFailures?.first()?.message == null
    }

    def "when getRecord called it calls manager with provided input"() {
        given: "Not null key is provided"
        def key = 'test-id'

        when: "Orchestrator called with the given key"
        orchestrator.getRecord(key)

        then: "Orchestrator calls manager with this key"
        1 * manager.getRecord(key)
    }

    def "when getRecord called and exception is thrown by manager it just propagates"() {
        given: "Not null test key"
        def key = 'test-id'

        when: "Orchestrator called with the given key"
        orchestrator.getRecord(key)

        then: "Manager throws exception"
        1 * manager.getRecord(key) >> { throw new RecordNotFoundException(key) }
        then: "Exception just propagates"
        thrown(RecordNotFoundException)
    }

    def "when deleteRecord called it calls manager with provided input"() {
        given: "Not null key is provided"
        def key = 'test-id'

        when: "Orchestrator called with the given key"
        orchestrator.deleteRecord(key)

        then: "Orchestrator calls manager with this key"
        1 * manager.deleteRecord(key)
    }

    def "when deleteRecord called and exception is thrown by manager it just propagates"() {
        given: "Null test key"
        def key = null

        when: "Orchestrator called with the given key"
        orchestrator.deleteRecord(key)

        then: "Manager throws exception"
        1 * manager.deleteRecord(key) >> { throw new IllegalArgumentException() }
        then: "Exception just propagates"
        thrown(IllegalArgumentException)
    }

    private MockMultipartFile prepareMockFile(String line) {
        new MockMultipartFile('snapshot', 'snapshot.cvs',
            MediaType.MULTIPART_FORM_DATA_VALUE,
            line.getBytes(Charset.forName("UTF-8")))
    }

}
