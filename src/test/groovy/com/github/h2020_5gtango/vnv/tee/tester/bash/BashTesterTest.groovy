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

package com.github.h2020_5gtango.vnv.tee.tester.bash

import com.github.h2020_5gtango.vnv.tee.model.TestSuiteResult
import com.github.h2020_5gtango.vnv.tee.restmock.TestResultRepositoryMock
import com.github.mrduguo.spring.test.AbstractSpec
import org.mapstruct.Named
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

import javax.annotation.Resource

class BashTesterTest extends AbstractSpec {

    @Value('${app.test.suite.id}')
    def testUuid

    @Autowired
    BashTester bash

    def testSuiteResult = new TestSuiteResult()


    void "bashTester should return the details from details.json file"() {
        given:
        def workspace = new File('/tmp')
        def mockRunnerFile = new File(bash.RUNNER_EXECUTABLE_FILE)
        //def detailsFile = new File(workspace, bash.DETAILS_JSON_FILE)
        def detailsFile = new File(bash.DETAILS_JSON_FILE)

        detailsFile << loadDetailsData()
        mockRunnerFile << loadRunnerFile()

        when:
        def result = bash.execute(workspace, testSuiteResult)

        then:
        //fixme: paths
        result.details.name == 'John Smith'
        result.status == 'SUCCESS'

        cleanup:
        mockRunnerFile.delete()
        detailsFile.delete()
    }

    void "bashTester should return the result from result.log file"() {

        given:
        def workspace = new File('/tmp')
        //fixme: make this a test
        //def mockRunnerFile = new File(workspace, bash.RUNNER_EXECUTABLE_FILE)
        def mockRunnerFile = new File(bash.RUNNER_EXECUTABLE_FILE)
        //def resultFile = new File(workspace,bash.RESULTS_LOG_FILE)
        def resultFile = new File(bash.RESULTS_LOG_FILE)
        resultFile << loadResultData()

        when:
        def result = bash.execute(workspace, testSuiteResult)

        then:
        result.details!='foo'
        //fixme: make this a test
//        resultFile.text == loadResultData()

        cleanup:
        mockRunnerFile.delete()
        resultFile.delete()
    }

    String loadDetailsDataOld() {
        '{"name": "John Smith" } /* some comment */'
    }

    String loadDetailsData() {
        '{\n' +

                '    "name": "John Smith",\n' +
                '    "requests": 39166,\n' +
                '    "duration_in_microseconds": 100048225.00,\n' +
                '    "bytes": 7324067,\n' +
                '    "requests_per_sec": 391.47,\n' +
                '    "bytes_transfer_per_sec": 73205.37,\n' +
                '    "latency_distribution": [\n' +
                '        {\n' +
                '            "percentile": 50,\n' +
                '            "latency_in_microseconds": 44400639\n' +
                '        },\n' +
                '        {\n' +
                '            "percentile": 75,\n' +
                '            "latency_in_microseconds": 62324735\n' +
                '        },\n' +
                '        {\n' +
                '            "percentile": 90,\n' +
                '            "latency_in_microseconds": 73203711\n' +
                '        },\n' +
                '        {\n' +
                '            "percentile": 99,\n' +
                '            "latency_in_microseconds": 79757311\n' +
                '        },\n' +
                '        {\n' +
                '            "percentile": 99.9,\n' +
                '            "latency_in_microseconds": 81657855\n' +
                '        },\n' +
                '        {\n' +
                '            "percentile": 99.99,\n' +
                '            "latency_in_microseconds": 82640895\n' +
                '        },\n' +
                '        {\n' +
                '            "percentile": 99.999,\n' +
                '            "latency_in_microseconds": 83099647\n' +
                '        },\n' +
                '        {\n' +
                '            "percentile": 100,\n' +
                '            "latency_in_microseconds": 83099647\n' +
                '        }\n' +
                '    ]\n' +
                '}'
    }
    String loadResultData() {
        'Running 30s test @ http://127.0.0.1:80/index.html\n' +
                '  2 threads and 100 connections\n' +
                '  Thread calibration: mean lat.: 10087 usec, rate sampling interval: 22 msec\n' +
                '  Thread calibration: mean lat.: 10139 usec, rate sampling interval: 21 msec\n' +
                '  Thread Stats        Avg               Stdev             Max             +/- Stdev\n' +
                '    Latency' + '\t' + '6.60ms' + '\t' + '1.92ms' + '\t' + '12.50ms' + '\t' + '68.46%\n' +
                '    Req/Sec' + '\t' +  '1.04k' + '\t' +  '1.08k' + '\t' +   '2.50k' + '\t' + '72.79%\n' +
                '  Latency Distribution (HdrHistogram - Recorded Latency)\n' +
                ' 50.000%' + '\t' + '6.67ms\n' +
                ' 75.000%' + '\t' + '7.78ms\n' +
                ' 90.000%' + '\t' + '9.14ms\n' +
                ' 99.000%' + '\t' + '11.18ms\n' +
                ' 99.900%' + '\t' + '12.30ms\n' +
                ' 99.990%' + '\t' + '12.45ms\n' +
                ' 99.999%' + '\t' + '12.50ms\n' +
                '100.000%' + '\t' + '12.50ms\n' +
                '\n' +
                'Detailed Percentile spectrum:\n' +
                '     Value           Percentile           TotalCount       1/(1-Percentile)\n' +
                '     \n' +
                '     0.921' + '\t' + '0.000000' + '\t' +     '1' + '\t' + '1.00 \n' +
                '     4.053' + '\t' + '0.100000' + '\t' +  '3951' + '\t' + '1.11\n' +
                '     4.935' + '\t' + '0.200000' + '\t' +  '7921' + '\t' + '1.25\n' +
                '     5.627' + '\t' + '0.300000' + '\t' + '11858' + '\t' + '1.43\n' +
                '     6.179' + '\t' + '0.400000' + '\t' + '15803' + '\t' + '1.67\n' +
                '     6.671' + '\t' + '0.500000' + '\t' + '19783' + '\t' + '2.00\n' +
                '     6.867' + '\t' + '0.550000' + '\t' + '21737' + '\t' + '2.22\n' +
                '     7.079' + '\t' + '0.600000' + '\t' + '23733' + '\t' + '2.50\n' +
                '     7.287' + '\t' + '0.650000' + '\t' + '25698' + '\t' + '2.86\n' +
                '     7.519' + '\t' + '0.700000' + '\t' + '27659' + '\t' + '3.33\n' +
                '     7.783' + '\t' + '0.750000' + '\t' + '29644' + '\t' + '4.00\n' +
                '     7.939' + '\t' + '0.775000' + '\t' + '30615' + '\t' + '4.44\n' +
                '     8.103' + '\t' + '0.800000' + '\t' + '31604' + '\t' + '5.00\n' +
                '     8.271' + '\t' + '0.825000' + '\t' + '32597' + '\t' + '5.71\n' +
                '     8.503' + '\t' + '0.850000' + '\t' + '33596' + '\t' + '6.67\n' +
                '     8.839' + '\t' + '0.875000' + '\t' + '34571' + '\t' + '8.00\n' +
                '     9.015' + '\t' + '0.887500' + '\t' + '35070' + '\t' + '8.89\n' +
                '     9.143' + '\t' + '0.900000' + '\t' + '35570' + '\t' + '10.00\n' +
                '     9.335' + '\t' + '0.912500' + '\t' + '36046' + '\t' + '11.43\n' +
                '     9.575' + '\t' + '0.925000' + '\t' + '36545' + '\t' + '13.33\n' +
                '     9.791' + '\t' + '0.937500' + '\t' + '37032' + '\t' + '16.00\n' +
                '     9.903' + '\t' + '0.943750' + '\t' + '37280' + '\t' + '17.78\n' +
                '    10.015' + '\t' + '0.950000' + '\t' + '37543' + '\t' + '20.00\n' +
                '    10.087' + '\t' + '0.956250' + '\t' + '37795' + '\t' + '22.86\n' +
                '    10.167' + '\t' + '0.962500' + '\t' + '38034' + '\t' + '26.67\n' +
                '    10.279' + '\t' + '0.968750' + '\t' + '38268' + '\t' + '32.00\n' +
                '    10.343' + '\t' + '0.971875' + '\t' + '38390' + '\t' + '35.56\n' +
                '    10.439' + '\t' + '0.975000' + '\t' + '38516' + '\t' + '40.00\n' +
                '    10.535' + '\t' + '0.978125' + '\t' + '38636' + '\t' + '45.71\n' +
                '    10.647' + '\t' + '0.981250' + '\t' + '38763' + '\t' + '53.33\n' +
                '    10.775' + '\t' + '0.984375' + '\t' + '38884' + '\t' + '64.00\n' +
                '    10.887' + '\t' + '0.985938' + '\t' + '38951' + '\t' + '71.11\n' +
                '    11.007' + '\t' + '0.987500' + '\t' + '39007' + '\t' + '80.00\n' +
                '    11.135' + '\t' + '0.989062' + '\t' + '39070' + '\t' + '91.43\n' +
                '    11.207' + '\t' + '0.990625' + '\t' + '39135' + '\t' + '106.67\n' +
                '    11.263' + '\t' + '0.992188' + '\t' + '39193' + '\t' + '128.00\n' +
                '    11.303' + '\t' + '0.992969' + '\t' + '39226' + '\t' + '142.22\n' +
                '    11.335' + '\t' + '0.993750' + '\t' + '39255' + '\t' + '160.00\n' +
                '    11.367' + '\t' + '0.994531' + '\t' + '39285' + '\t' + '182.86\n' +
                '    11.399' + '\t' + '0.995313' + '\t' + '39319' + '\t' + '213.33\n' +
                '    11.431' + '\t' + '0.996094' + '\t' + '39346' + '\t' + '256.00\n' +
                '    11.455' + '\t' + '0.996484' + '\t' + '39365' + '\t' + '284.44\n' +
                '    11.471' + '\t' + '0.996875' + '\t' + '39379' + '\t' + '320.00\n' +
                '    11.495' + '\t' + '0.997266' + '\t' + '39395' + '\t' + '365.71\n' +
                '    11.535' + '\t' + '0.997656' + '\t' + '39408' + '\t' + '426.67\n' +
                '    11.663' + '\t' + '0.998047' + '\t' + '39423' + '\t' + '512.00\n' +
                '    11.703' + '\t' + '0.998242' + '\t' + '39431' + '\t' + '568.89\n' +
                '    11.743' + '\t' + '0.998437' + '\t' + '39439' + '\t' + '640.00\n' +
                '    11.807' + '\t' + '0.998633' + '\t' + '39447' + '\t' + '731.43\n' +
                '    12.271' + '\t' + '0.998828' + '\t' + '39454' + '\t' + '853.33\n' +
                '    12.311' + '\t' + '0.999023' + '\t' + '39463' + '\t' + '1024.00\n' +
                '    12.327' + '\t' + '0.999121' + '\t' + '39467' + '\t' + '1137.78\n' +
                '    12.343' + '\t' + '0.999219' + '\t' + '39470' + '\t' + '1280.00\n' +
                '    12.359' + '\t' + '0.999316' + '\t' + '39473' + '\t' + '1462.86\n' +
                '    12.375' + '\t' + '0.999414' + '\t' + '39478' + '\t' + '1706.67\n' +
                '    12.391' + '\t' + '0.999512' + '\t' + '39482' + '\t' + '2048.00\n' +
                '    12.399' + '\t' + '0.999561' + '\t' + '39484' + '\t' + '2275.56\n' +
                '    12.407' + '\t' + '0.999609' + '\t' + '39486' + '\t' + '2560.00\n' +
                '    12.415' + '\t' + '0.999658' + '\t' + '39489' + '\t' + '2925.71\n' +
                '    12.415' + '\t' + '0.999707' + '\t' + '39489' + '\t' + '3413.33\n' +
                '    12.423' + '\t' + '0.999756' + '\t' + '39491' + '\t' + '4096.00\n' +
                '    12.431' + '\t' + '0.999780' + '\t' + '39493' + '\t' + '4551.11\n' +
                '    12.431' + '\t' + '0.999805' + '\t' + '39493' + '\t' + '5120.00\n' +
                '    12.439' + '\t' + '0.999829' + '\t' + '39495' + '\t' + '5851.43\n' +
                '    12.439' + '\t' + '0.999854' + '\t' + '39495' + '\t' + '6826.67\n' +
                '    12.447' + '\t' + '0.999878' + '\t' + '39496' + '\t' + '8192.00.'
    }
    String loadRunnerFile() {
        "#!bin/bash \n ls"
    }
}
