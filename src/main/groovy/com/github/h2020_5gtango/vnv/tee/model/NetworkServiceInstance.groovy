package com.github.h2020_5gtango.vnv.tee.model


class NetworkServiceInstance {

    String uuid

    String status
    String version

    Date createdAt
    Date updatedAt

    List forwardingGraphs
    List networkFunctions
    List virtualLinks

    Map connectionPoints=[:]

}
