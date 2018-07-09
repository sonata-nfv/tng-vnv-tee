package com.github.h2020_5gtango.vnv.tee.status

import com.github.h2020_5gtango.vnv.tee.model.NetworkService
import com.github.h2020_5gtango.vnv.tee.restclient.SpConnector
import com.github.h2020_5gtango.vnv.tee.restclient.TestResultRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ServicesStatus {

    @Autowired
    SpConnector spConnector

    @Autowired
    TestResultRepository testResultRepository

    @GetMapping('/api/v1/status/services')
    List<NetworkService> listServicesStatus() {
        spConnector.listServices().collect {ns->
            ns.vnvStatus=testResultRepository.listByService(ns.uuid).first().status
            ns
        }
    }

}
