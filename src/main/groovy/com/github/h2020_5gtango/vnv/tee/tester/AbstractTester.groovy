/*
 * Copyright (c) 2015 SONATA-NFV, 2017 5GTANGO [, ANY ADDITIONAL AFFILIATION]
 * ALL RIGHTS RESERVED.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Neither the name of the SONATA-NFV, 5GTANGO [, ANY ADDITIONAL AFFILIATION]
 * nor the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * This work has been performed in the framework of the SONATA project,
 * funded by the European Commission under Grant number 671517 through
 * the Horizon 2020 and 5G-PPP programmes. The authors would like to
 * acknowledge the contributions of their colleagues of the SONATA
 * partner consortium (www.sonata-nfv.eu).
 *
 * This work has been performed in the framework of the 5GTANGO project,
 * funded by the European Commission under Grant number 761493 through
 * the Horizon 2020 and 5G-PPP programmes. The authors would like to
 * acknowledge the contributions of their colleagues of the 5GTANGO
 * partner consortium (www.5gtango.eu).
 */

package com.github.h2020_5gtango.vnv.tee.tester

import com.github.h2020_5gtango.vnv.tee.model.TestSuiteResult
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired

class AbstractTester implements Tester {

    public static String RUNNER_EXECUTABLE_FILE = 'runner.sh'
    public static String RESULTS_LOG_FILE = 'result.log'
    public static String DETAILS_JSON_FILE = 'details.json'

    @Autowired
    AbstractRunner bashRunner

    @Override
    TestSuiteResult execute(File workspace, TestSuiteResult testSuiteResult) {
        testSuiteResult = executeShell(workspace, testSuiteResult)

        testSuiteResult
    }

    TestSuiteResult executeShell(File workspace, TestSuiteResult testSuiteResult) {
        def result = bashRunner.run(new File(RUNNER_EXECUTABLE_FILE))
        testSuiteResult.status = result.exitValue == 0 ? 'SUCCESS' : 'FAILED'
        testSuiteResult.stout = result.stout?.toString()
        testSuiteResult.sterr = result.sterr?.toString()

        testSuiteResult.testerResultText=attachRawData(new File(workspace, RESULTS_LOG_FILE))
        testSuiteResult.details=attachJsonData(new File(workspace, DETAILS_JSON_FILE))

        testSuiteResult
    }

    def attachRawData(File file){
        (file.exists()) ? file.text : null
    }

    def attachJsonData(File file){
        (file.exists()) ? new JsonSlurper().parseText(file.text) :  null

    }
}
