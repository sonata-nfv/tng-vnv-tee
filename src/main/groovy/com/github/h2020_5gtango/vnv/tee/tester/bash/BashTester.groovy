package com.github.h2020_5gtango.vnv.tee.tester.bash

import com.github.h2020_5gtango.vnv.tee.model.TestSuiteResult
import com.github.h2020_5gtango.vnv.tee.tester.Tester
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component('bash')
class BashTester implements Tester {

    @Autowired
    AbstractRunner bashRunner

    Map exexuteSh(File runnerSh) {
        bashRunner.exexuteSh(runnerSh)
    }

    @Override
    TestSuiteResult execute(File workspace, TestSuiteResult testSuiteResult) {
        def result = exexuteSh(new File(workspace, 'runner.sh'))
        testSuiteResult.status = result.exitValue == 0 ? 'SUCCESS' : 'FAILED'
        testSuiteResult.stout = result.stout?.toString()
        testSuiteResult.sterr = result.sterr?.toString()

        def resultTextFile = new File(workspace, 'result.log')
        if(resultTextFile.exists()){
            testSuiteResult.testerResultText=resultTextFile.text
        }
        testSuiteResult
    }
}
