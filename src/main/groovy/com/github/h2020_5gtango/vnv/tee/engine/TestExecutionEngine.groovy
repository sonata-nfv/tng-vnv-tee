package com.github.h2020_5gtango.vnv.tee.engine

import com.github.h2020_5gtango.vnv.tee.model.TestSuite
import com.github.h2020_5gtango.vnv.tee.model.TestSuiteResult
import com.github.h2020_5gtango.vnv.tee.restclient.TestCatalogue
import com.github.h2020_5gtango.vnv.tee.restclient.TestResultRepository
import com.github.h2020_5gtango.vnv.tee.tester.Tester
import groovy.text.SimpleTemplateEngine
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestExecutionEngine {

    @Autowired
    TestResultRepository testResultRepository

    @Autowired
    TestCatalogue testCatalogue

    @Autowired
    Map<String, Tester> testers

    @PostMapping('/api/v1/test-suite-results')
    TestSuiteResult executeTestAgainstNs(@RequestBody TestSuiteResult testSuiteResult) {
        testSuiteResult=testResultRepository.createTestSuiteResult(testSuiteResult)
        def packageMetadata = testCatalogue.loadPackageMetadata(testSuiteResult.packageId)
        def testSuite = testCatalogue.loadTestSuite(testSuiteResult.testSuiteId)
        def testWorkspace = testCatalogue.downloadTestSuiteResources(packageMetadata,testSuite, testSuiteResult.uuid)
        def nsi = testResultRepository.loadNetworkServiceInstance(testSuiteResult.networkServiceInstanceId)
        applyTemplating(testSuite, testWorkspace, nsi)
        testSuiteResult = testers[testSuite.type].execute(testWorkspace, testSuiteResult)
        testResultRepository.updateTestSuiteResult(testSuiteResult)
    }

    List<TestSuite.TestResource> applyTemplating(testSuite, testWorkspace, nsi) {
        testSuite.testResources.each { testResource ->
            if (testResource.contentType.contains('text')) {
                def targetFile = new File(testWorkspace, testResource.target ?: testResource.source)
                def binding = [
                        nsi      : nsi,
                        testSuite: testSuite,
                        workspace: testWorkspace,
                ]
                def replacedText = new SimpleTemplateEngine().createTemplate(targetFile.text).make(binding).toString()
                targetFile.delete()
                targetFile << replacedText
            }
        }
    }

}
