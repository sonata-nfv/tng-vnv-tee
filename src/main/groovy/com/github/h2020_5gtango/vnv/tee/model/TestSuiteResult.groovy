package com.github.h2020_5gtango.vnv.tee.model

import groovy.transform.EqualsAndHashCode
import io.swagger.annotations.ApiModelProperty

import javax.validation.constraints.NotNull
import java.security.MessageDigest

@EqualsAndHashCode
class TestSuiteResult {

    String packageId
    String uuid
    String testPlanId
    String networkServiceInstanceId

    @ApiModelProperty(required = true)
    @NotNull
    String testSuiteId

    String status

    Map details

    String cliOutputText
}
