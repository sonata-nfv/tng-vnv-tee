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
