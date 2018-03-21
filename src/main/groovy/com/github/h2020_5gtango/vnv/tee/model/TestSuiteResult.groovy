package com.github.h2020_5gtango.vnv.tee.model

import groovy.transform.EqualsAndHashCode

import java.security.MessageDigest

@EqualsAndHashCode
class TestSuiteResult {

    String testSuiteResultId

    String testPlanId
    String networkServiceInstanceId
    String testSuiteId

    String status

    Map details
}
