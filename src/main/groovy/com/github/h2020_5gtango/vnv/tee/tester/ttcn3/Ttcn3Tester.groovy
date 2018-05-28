package com.github.h2020_5gtango.vnv.tee.tester.ttcn3

import com.github.h2020_5gtango.vnv.tee.model.TestSuiteResult
import com.github.h2020_5gtango.vnv.tee.tester.bash.BashTester
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component('ttcn3')
class Ttcn3Tester extends BashTester {

    @Autowired
    Ttcn3ResultParser resultParser

    @Override
    TestSuiteResult execute(File workspace, TestSuiteResult testSuiteResult) {
        def result = exexuteSh(new File(workspace, 'runner.sh'))
        if (result.exitValue != 0) {
            testSuiteResult.status = 'ERROR'
        } else {
            testSuiteResult=resultParser.parse(workspace,testSuiteResult)
        }
        testSuiteResult.stout = result.stout?.toString()
        testSuiteResult.sterr = result.sterr?.toString()
        testSuiteResult
    }
}
