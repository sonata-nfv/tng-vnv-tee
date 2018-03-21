package com.github.h2020_5gtango.vnv.tee.model

import groovy.transform.EqualsAndHashCode
import io.swagger.annotations.ApiModelProperty

import javax.validation.constraints.NotNull

@EqualsAndHashCode
class TestSuite {
    @ApiModelProperty(required = true)
    @NotNull
    String testSuiteId

    String name
    String vendor
    String version
    String type

    List<TestResource> testResources


    static class TestResource{
        String source
        String target
        String contentType
    }
}
