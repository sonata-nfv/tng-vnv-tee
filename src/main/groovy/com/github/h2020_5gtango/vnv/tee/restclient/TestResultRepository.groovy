package com.github.h2020_5gtango.vnv.tee.restclient

import com.github.h2020_5gtango.vnv.tee.model.NetworkServiceInstance
import com.github.h2020_5gtango.vnv.tee.model.TestSuiteResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
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

    @Value('${app.trr.network.function.instance.load.endpoint}')
    def networkFunctionInstanceLoadEndpoint

    TestSuiteResult createTestSuiteResult(TestSuiteResult testSuiteResult) {
        testSuiteResult.status='SCHEDULED'
        def headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        def entity = new HttpEntity<TestSuiteResult>(testSuiteResult ,headers)
        restTemplate.postForEntity(testSuiteResultCreateEndpoint,entity,TestSuiteResult).body
    }

    TestSuiteResult updateTestSuiteResult(TestSuiteResult testSuiteResult) {
        def headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        def entity = new HttpEntity<TestSuiteResult>(testSuiteResult ,headers)
        restTemplate.exchange(testSuiteResultUpdateEndpoint, HttpMethod.PUT, entity, TestSuiteResult.class ,testSuiteResult.uuid).body
    }

    NetworkServiceInstance loadNetworkServiceInstance(String instanceUuid) {
        NetworkServiceInstance networkServiceInstance = restTemplate.getForEntity(networkServiceInstanceLoadEndpoint, NetworkServiceInstance, instanceUuid).body
        networkServiceInstance.networkFunctions?.each{vnf->
            def vnfi = restTemplate.getForEntity(networkFunctionInstanceLoadEndpoint, Object.class, vnf.vnfr_id).body
            vnf.vnfi=vnfi
            vnfi.virtual_deployment_units?.each{unit->
                unit.vnfc_instance?.each{vnfc_instance->
                    def points=[:]
                    networkServiceInstance.connectionPoints.put(unit.vdu_reference,points)
                    vnfc_instance.connection_points?.each{connection_point->
                        points.put(connection_point.id,connection_point)
                    }

                }
            }
        }
        networkServiceInstance
    }
}
