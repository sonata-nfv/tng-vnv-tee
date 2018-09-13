/*
 * Copyright (c) 2015 SONATA-NFV, 2017 5GTANGO [, ANY ADDITIONAL AFFILIATION]
 * ALL RIGHTS RESERVED.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Neither the name of the SONATA-NFV, 5GTANGO [, ANY ADDITIONAL AFFILIATION]
 * nor the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * This work has been performed in the framework of the SONATA project,
 * funded by the European Commission under Grant number 671517 through
 * the Horizon 2020 and 5G-PPP programmes. The authors would like to
 * acknowledge the contributions of their colleagues of the SONATA
 * partner consortium (www.sonata-nfv.eu).
 *
 * This work has been performed in the framework of the 5GTANGO project,
 * funded by the European Commission under Grant number 761493 through
 * the Horizon 2020 and 5G-PPP programmes. The authors would like to
 * acknowledge the contributions of their colleagues of the 5GTANGO
 * partner consortium (www.5gtango.eu).
 */

package com.github.h2020_5gtango.vnv.tee.restmock

import com.github.h2020_5gtango.vnv.tee.model.NetworkServiceInstance
import com.github.h2020_5gtango.vnv.tee.model.TestSuiteResult
import groovy.util.logging.Log
import org.springframework.web.bind.annotation.*

@Log
@RestController
class TestResultRepositoryMock {

    Map<String, TestSuiteResult> testSuiteResults = [:]

    void reset() {
        testSuiteResults.clear()
    }

    @PostMapping('/mock/trr/test-suite-results')
    TestSuiteResult createTestSuiteResult(@RequestBody TestSuiteResult testSuiteResult) {
        log.info("##vnvlog-v.2: testSuiteResult.uuid is ${testSuiteResult.uuid}")
        if(!testSuiteResult.uuid) {
            testSuiteResult.uuid = UUID.randomUUID().toString()
        }
        testSuiteResults[testSuiteResult.uuid] = testSuiteResult
    }

    @PostMapping('/mock/trr/test-suite-results/{testSuiteResultId:.+}')
    TestSuiteResult updateTestSuiteResult(
            @RequestBody TestSuiteResult testSuiteResult, @PathVariable('testSuiteResultId') String testSuiteResultId) {
        testSuiteResults[testSuiteResultId] = testSuiteResult
    }

    @GetMapping('/mock/trr/network-service-instance-instances/{instanceUuid}')
    NetworkServiceInstance loadNetworkServiceInstance(
            @PathVariable('instanceUuid') String instanceUuid) {
        new NetworkServiceInstance(
                instanceUuid: instanceUuid,
                runtime: [
                        host: [
                                ip: '8.8.8.8'
                        ]
                ]
        )
    }

}
