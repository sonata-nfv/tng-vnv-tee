package com.github.h2020_5gtango.vnv.tee.model

class Wrk2ResultDetails {
    String requests
    String duration_in_microseconds
    String bytes
    String requests_per_sec
    String bytes_transfer_per_sec
    List<LatencyPercentile> latency_distribution
}

class LatencyPercentile {
    String percentile
    String latency_in_microseconds
}

