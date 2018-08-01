package com.github.h2020_5gtango.vnv.tee.tester.wrk2

import com.github.h2020_5gtango.vnv.tee.model.TestSuiteResult
import org.springframework.stereotype.Component

@Component
class Wrk2ResultParser {

    TestSuiteResult parse(File workspace, TestSuiteResult testSuiteResult) {
        def result = [:]
        def resultTextFile = new File(workspace, 'result.log')
        resultTextFile.eachLine {line->
            if(line.contains('- Verdict statistics: ')){
                line.split(': ')[1].split(', ').each {sum->
                    def sumResult=sum.split(' ')
                    result[sumResult[1]]=sumResult[0].toInteger()
                }
            }
        }

        if (result.fail || result.error) {
            testSuiteResult.status = 'FAILED'
        } else if (result.pass || result.none) {
            testSuiteResult.status = 'SUCCESS'
        } else {
            testSuiteResult.status = 'INVALID_TEST_RESULT'
        }
        testSuiteResult.details=result
        testSuiteResult.testerResultText=resultTextFile.text
        testSuiteResult
    }
}
