package eu.h2020_5gtango.vnv.tee.tester

import eu.h2020_5gtango.vnv.tee.model.TestSuiteResult

interface Tester {
    TestSuiteResult execute(File workspace, TestSuiteResult testSuiteResult)
}
