package com.github.h2020_5gtango.vnv.tee.status

import com.github.h2020_5gtango.vnv.tee.model.NetworkService
import com.github.h2020_5gtango.vnv.tee.model.Test
import com.github.h2020_5gtango.vnv.tee.restclient.SpConnector
import com.github.h2020_5gtango.vnv.tee.restclient.TestCatalogue
import com.github.h2020_5gtango.vnv.tee.restclient.TestResultRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestsStatus {

    @Autowired
    TestCatalogue testCatalogue

    @Autowired
    TestResultRepository testResultRepository

    @GetMapping('/api/v1/status/tests')
    List<Test> listTestsStatus() {
        testCatalogue.listTests().collect {test->
            test.vnvStatus=testResultRepository.listByTest(test.uuid).first().status
            test
        }
    }

}
