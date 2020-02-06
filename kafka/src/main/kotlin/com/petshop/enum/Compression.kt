package com.rjdesenvolvimento.messagebroker.enums

enum class Compression(val value: String) {
    None("none"),
    Gzip("gzip"),
    Snappy("snappy"),
    lz4("lz4"),
    zstd("zstd")
}