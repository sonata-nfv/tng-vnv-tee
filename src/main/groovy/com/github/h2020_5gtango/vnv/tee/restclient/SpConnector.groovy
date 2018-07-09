package com.github.h2020_5gtango.vnv.tee.restclient

import com.github.h2020_5gtango.vnv.tee.model.NetworkService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class SpConnector {

    @Autowired
    @Qualifier('restTemplateWithoutAuth')
    RestTemplate restTemplate

    @Value('${app.sp.service.list.endpoint}')
    def serviceListEndpoint

    List<NetworkService> listServices() {
        restTemplate.getForEntity(serviceListEndpoint, NetworkService[].class).body
    }

}
