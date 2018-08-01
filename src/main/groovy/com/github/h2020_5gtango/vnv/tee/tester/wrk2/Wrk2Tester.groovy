package com.github.h2020_5gtango.vnv.tee.tester.wrk2

import com.github.h2020_5gtango.vnv.tee.model.TestSuiteResult
import com.github.h2020_5gtango.vnv.tee.tester.AbstractTester
import com.github.h2020_5gtango.vnv.tee.tester.bash.BashTester
import com.github.h2020_5gtango.vnv.tee.tester.wrk2.Wrk2ResultParser
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component('wrk2')
class Wrk2Tester extends AbstractTester {

    @Autowired
    Wrk2ResultParser resultParser

    @Override
    TestSuiteResult execute(File workspace, TestSuiteResult testSuiteResult) {
        def resultFile = new File(workspace,'result.log')
        if (testSuiteResult.status != 'ERROR' && resultFile)
            testSuiteResult=resultParser.parse(workspace,testSuiteResult)

        testSuiteResult
    }
}
