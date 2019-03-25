package com.example.data.snapshots.storage.api

import com.example.data.snapshots.storage.IntegrationTest
import com.example.data.snapshots.storage.config.WebConfig
import com.example.data.snapshots.storage.service.SnapshotProcessingOrchestrator
import org.junit.experimental.categories.Category
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static java.nio.charset.StandardCharsets.UTF_8
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(DataSnapshotsStorageController)
@ContextConfiguration(classes = [WebConfig, Mocks])
@TestPropertySource(locations = "classpath:application-test.yaml")
@Category(IntegrationTest)
class DataSnapshotsStorageControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    def "when send post with plain text file then status is OK"() {
        given:
        def file = new MockMultipartFile(
                "snapshot",
                "PRIMARY_KEY,NAME,DESCRIPTION,UPDATED_TIMESTAMP\n".getBytes(UTF_8))

        when:
        def results = mockMvc.perform(multipart("/data/snapshot/upload").file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .characterEncoding(UTF_8.name()))

        then:
        results.andExpect(status().isOk())
    }

    def "when send post with null content then status is noT OK"() {
        given:
        def file = new MockMultipartFile("snapshot", null)

        when:
        def results = mockMvc.perform(multipart("/data/snapshot/upload").file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .characterEncoding(UTF_8.name()))

        then:
        results.andExpect(status().isOk())
    }

    def "when send get request with key as parameter status is OK and request contains value"() {
        when:
        def results = mockMvc.perform(get("/data/snapshot/records/1"))

        then:
        results.andExpect(status().isOk())
        results.andExpect(jsonPath('$.primaryKey').exists())
        results.andExpect(jsonPath('$.name').exists())
        results.andExpect(jsonPath('$.description').exists())
        results.andExpect(jsonPath('$.updatedTime').exists())
    }

    def "when send delete request with key as parameter status is no content"() {
        when:
        def results = mockMvc.perform(delete("/data/snapshot/records/1"))

        then:
        results.andExpect(status().isNoContent())
    }

    @TestConfiguration
    static class Mocks {
        def factory = new DetachedMockFactory()

        @Bean
        SnapshotProcessingOrchestrator snapshotProcessingOrchestrator() {
            factory.Mock(SnapshotProcessingOrchestrator)
        }
    }

}
