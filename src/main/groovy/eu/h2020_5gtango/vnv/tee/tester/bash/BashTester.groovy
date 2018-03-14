package eu.h2020_5gtango.vnv.tee.tester.bash

import eu.h2020_5gtango.vnv.tee.model.TestSuiteResult
import eu.h2020_5gtango.vnv.tee.tester.Tester
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
        testSuiteResult
    }
}
