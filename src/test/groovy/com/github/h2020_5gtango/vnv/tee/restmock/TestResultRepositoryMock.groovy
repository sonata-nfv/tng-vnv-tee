package com.github.h2020_5gtango.vnv.tee.restmock

import com.github.h2020_5gtango.vnv.tee.model.NetworkServiceInstance
import com.github.h2020_5gtango.vnv.tee.model.TestSuiteResult
import org.springframework.web.bind.annotation.*

@RestController
class TestResultRepositoryMock {

    Map<String, TestSuiteResult> testSuiteResults = [:]

    void reset() {
        testSuiteResults.clear()
    }

    @PostMapping('/mock/trr/test-suite-results')
    TestSuiteResult createTestSuiteResult(@RequestBody TestSuiteResult testSuiteResult) {
        testSuiteResults[testSuiteResult.testSuiteResultId] = testSuiteResult
    }

    @PostMapping('/mock/trr/test-suite-results/{testSuiteResultId:.+}')
    TestSuiteResult updateTestSuiteResult(
            @RequestBody TestSuiteResult testSuiteResult, @PathVariable('testSuiteResultId') String testSuiteResultId) {
        testSuiteResults[testSuiteResultId] = testSuiteResult
    }

    @GetMapping('/mock/trr/network-service-instance-instances/{networkServiceInstanceId}')
    NetworkServiceInstance loadNetworkServiceInstance(
            @PathVariable('networkServiceInstanceId') String networkServiceInstanceId) {
        new NetworkServiceInstance(
                networkServiceInstanceId: networkServiceInstanceId,
                runtime: [
                        host: [
                                ip: '8.8.8.8'
                        ]
                ]
        )
    }

}
