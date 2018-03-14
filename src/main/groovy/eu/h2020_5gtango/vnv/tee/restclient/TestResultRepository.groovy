package eu.h2020_5gtango.vnv.tee.restclient

import eu.h2020_5gtango.vnv.tee.model.NetworkServiceInstance
import eu.h2020_5gtango.vnv.tee.model.TestSuite
import eu.h2020_5gtango.vnv.tee.model.TestSuiteResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class TestResultRepository {

    @Autowired
    @Qualifier('restTemplateWithAuth')
    RestTemplate restTemplate

    @Value('${app.trr.test.suite.result.create.endpoint}')
    def testSuiteResultCreateEndpoint

    @Value('${app.trr.test.suite.result.update.endpoint}')
    def testSuiteResultUpdateEndpoint

    @Value('${app.trr.network.service.instance.load.endpoint}')
    def networkServiceInstanceLoadEndpoint

    TestSuiteResult createTestSuiteResult(TestSuiteResult testSuiteResult) {
        testSuiteResult.testSuiteResultId = UUID.randomUUID().toString()
        testSuiteResult.status='SCHEDULED'
        restTemplate.postForEntity(testSuiteResultCreateEndpoint,testSuiteResult,TestSuiteResult).body
    }

    TestSuiteResult updateTestSuiteResult(TestSuiteResult testSuiteResult) {
        restTemplate.postForEntity(testSuiteResultUpdateEndpoint,testSuiteResult,TestSuiteResult,testSuiteResult.testSuiteResultId).body
    }

    NetworkServiceInstance loadNetworkServiceInstance(String networkServiceInstanceId) {
        restTemplate.getForEntity(networkServiceInstanceLoadEndpoint,NetworkServiceInstance,networkServiceInstanceId).body
    }
}
