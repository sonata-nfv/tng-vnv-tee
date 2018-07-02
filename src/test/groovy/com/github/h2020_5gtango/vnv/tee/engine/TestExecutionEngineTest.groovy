package com.github.h2020_5gtango.vnv.tee.engine

import com.github.mrduguo.spring.test.AbstractSpec
import com.github.h2020_5gtango.vnv.tee.restmock.TestResultRepositoryMock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import spock.lang.Ignore

@Ignore
class TestExecutionEngineTest extends AbstractSpec {
    @Value('${app.test.package.id}')
    def testPackageId
    @Value('${app.test.plan.id}')
    def testPlanId
    @Value('${app.test.nsi.id}')
    def networkServiceInstanceId
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
                network_service_instance_id: 'network_service_instance_id',
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
                network_service_instance_id: networkServiceInstanceId,
                test_uuid             : testUuid,
        ], Map.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body.status == 'FAILED'
        testResultRepositoryMock.testSuiteResults.size()==1
        testResultRepositoryMock.testSuiteResults.values().first().status=='FAILED'
    }

}
