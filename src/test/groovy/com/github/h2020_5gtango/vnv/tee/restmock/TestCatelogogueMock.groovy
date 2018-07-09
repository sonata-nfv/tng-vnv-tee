package com.github.h2020_5gtango.vnv.tee.restmock

import com.github.h2020_5gtango.vnv.tee.model.TestSuite
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class TestCatelogogueMock {

    @GetMapping('/mock/catalogue/test-suites/{testUuid}')
    TestSuite loadTestSuite(@PathVariable('testUuid') String testUuid) {
        testUuid == 'ttcn3' ? new TestSuite(
                testUuid: testUuid,
                type: 'ttcn3',
                testResources: [
                        new TestSuite.TestResource(
                                source: 'image.png',
                                target: 'bar.png',
                                contentType: 'application/octet-stream',
                        ),
                        new TestSuite.TestResource(
                                source: 'config.yml',
                                contentType: 'text/plain',
                        ),
                        new TestSuite.TestResource(
                                source: 'runner.sh',
                                contentType: 'text/plain',
                        ),
                        new TestSuite.TestResource(
                                source: 'result.log',
                                contentType: 'application/octet-stream',
                        ),
                ]
        ) :
                new TestSuite(
                        testUuid: testUuid,
                        type: 'bash',
                        testResources: [
                                new TestSuite.TestResource(
                                        source: 'runner.sh',
                                        contentType: 'text/plain',
                                ),
                        ]
                )
    }

    @GetMapping(
            value = '/mock/catalogue/test-suites/{testUuid}/download-resource',
            produces = ['application/octet-stream']
    )
    byte[] download(@PathVariable('testUuid') String testUuid, @RequestParam('source') String source) {
        new File("src/test/resources/test-suite-resources/$source").bytes
    }

}
