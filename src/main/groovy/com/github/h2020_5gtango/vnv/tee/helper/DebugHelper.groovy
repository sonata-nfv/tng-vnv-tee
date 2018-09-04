package com.github.h2020_5gtango.vnv.tee.helper

import groovy.util.logging.Log
import org.springframework.http.ResponseEntity

@Log
class DebugHelper {
    static def callExternalEndpoint(ResponseEntity responseEntity, def methodName, def endpoint, String message="ERROR CONNECTING WITH ENDPOINT"){
        if(responseEntity.statusCodeValue in [200, 201, 202, 203, 204, 205, 206, 207, 208, 226]) {
            loginfo("##vnvlog:$methodName call_endpoint: $endpoint, status: ${responseEntity.statusCode}")
        } else {
            logsevere("##vnvlog:$methodName $message: $endpoint, status: ${responseEntity.statusCode}")
            return null
        }

        responseEntity
    }

    static void loginfo(Object o) {
        log.info(o)
        println o
    }

    static void logsevere(Object o) {
        log.severe(o)
        println o
    }

}
