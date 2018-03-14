package eu.h2020_5gtango.vnv.tee.restclient

import eu.h2020_5gtango.vnv.tee.model.TestSuite
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class TestCatalogue {

    @Autowired
    @Qualifier('restTemplateWithAuth')
    RestTemplate restTemplate

    @Value('${app.catalogue.test.suite.load.endpoint}')
    def testSuiteLoadEndpoint

    @Value('${app.catalogue.resource.download.endpoint}')
    def resourceDownloadEndpoint

    @Value('${app.tee.tmp.dir}')
    File tmpDir

    TestSuite loadTestSuite(String testSuiteId ) {
        restTemplate.getForEntity(testSuiteLoadEndpoint,TestSuite,testSuiteId).body
    }

    File downloadTestSuiteResources(TestSuite testSuite,String testSuiteResultId) {
        def testSuiteWorkingDir=new File(tmpDir,testSuiteResultId)
        testSuite.testResources.each {testResource->
            def targetFile=new File(testSuiteWorkingDir,testResource.target?:testResource.source)
            targetFile.parentFile.mkdirs()
            targetFile.delete()
            targetFile << restTemplate.getForEntity(resourceDownloadEndpoint,byte[].class,testSuite.testSuiteId,testResource.source).body
        }
        testSuiteWorkingDir
    }
}
