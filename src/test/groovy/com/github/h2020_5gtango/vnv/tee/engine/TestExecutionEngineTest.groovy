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

package com.github.h2020_5gtango.vnv.tee.engine

import com.github.mrduguo.spring.test.AbstractSpec
import com.github.h2020_5gtango.vnv.tee.restmock.TestResultRepositoryMock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import spock.lang.Ignore

class TestExecutionEngineTest extends AbstractSpec {
    @Value('${app.test.package.id}')
    def testPackageId
    @Value('${app.test.plan.id}')
    def testPlanId
    @Value('${app.test.nsi.id}')
    def instanceUuid
    @Value('${app.test.suite.id}')
    def testUuid

    @Autowired
    TestResultRepositoryMock testResultRepositoryMock

    void "bash test should executed with success status"() {
        given:
        testResultRepositoryMock.reset()

        when:
        def entity = postForEntity('/tng-vnv-tee/api/v1/test-suite-results', [
                package_id              : 'package_id',
                test_plan_id              : 'test_plan_id',
                instance_uuid: 'instance_uuid',
                test_uuid             : 'test_uuid',
        ], Map.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body.status == 'SUCCESS'
        testResultRepositoryMock.testSuiteResults.size()==1
        testResultRepositoryMock.testSuiteResults.values().first().status=='SUCCESS'
    }

    void "ttcn3 test should executed with failed status"() {
        given:
        testResultRepositoryMock.reset()

        when:
        def entity = postForEntity('/tng-vnv-tee/api/v1/test-suite-results', [
                package_id              : testPackageId,
                test_plan_id              : testPlanId,
                instance_uuid: instanceUuid,
                test_uuid             : testUuid,
        ], Map.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body.status == 'FAILED'
        testResultRepositoryMock.testSuiteResults.size()==1
        testResultRepositoryMock.testSuiteResults.values().first().status=='FAILED'
    }

}
