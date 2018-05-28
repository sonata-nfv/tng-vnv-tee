package com.github.h2020_5gtango.vnv.tee.restclient

import com.github.h2020_5gtango.vnv.tee.model.PackageMetadata
import com.github.h2020_5gtango.vnv.tee.model.TestSuite
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class TestCatalogue {

    @Autowired
    @Qualifier('restTemplateWithAuth')
    RestTemplate restTemplateWithAuth

    @Autowired
    @Qualifier('restTemplateWithoutAuth')
    RestTemplate restTemplate


    @Value('${app.cat.package.metadata.endpoint}')
    def packageLoadEndpoint

    @Value('${app.cat.test.metadata.endpoint}')
    def testSuiteLoadEndpoint

    @Value('${app.cat.resource.download.endpoint}')
    def resourceDownloadEndpoint

    @Value('${app.tee.tmp.dir}')
    File tmpDir

    PackageMetadata loadPackageMetadata(String packageId ) {
        restTemplate.getForEntity(packageLoadEndpoint,PackageMetadata,packageId).body
    }

    TestSuite loadTestSuite(String testSuiteId ) {
        TestSuite testSuite=restTemplateWithAuth.getForEntity(testSuiteLoadEndpoint,TestSuite,testSuiteId).body
        testSuite.type=testSuite.testd.test_type
        testSuite.testd.test_configuration_parameters.each{param->
            if(param.containsKey('content_type')){
                testSuite.testResources.add(new TestSuite.TestResource(
                        contentType: param['content_type'],
                        source: 'Tests/'+param['parameter_value'],
                        target: param['parameter_value'],
                ))
            }
        }
        testSuite
    }

    File downloadTestSuiteResources(PackageMetadata packageMetadata,TestSuite testSuite,String testSuiteResultId) {
        def testSuiteWorkingDir=new File(tmpDir,testSuiteResultId)
        testSuite.testResources.each {testResource->
            def targetFile=new File(testSuiteWorkingDir,testResource.target?:testResource.source)
            targetFile.parentFile.mkdirs()
            targetFile.delete()
            def resourceUuid=packageMetadata.pd.package_content.find{it.source==testResource.source}.uuid
            targetFile << restTemplate.getForEntity(resourceDownloadEndpoint,byte[].class,packageMetadata.uuid,resourceUuid).body
        }
        testSuiteWorkingDir
    }
}
