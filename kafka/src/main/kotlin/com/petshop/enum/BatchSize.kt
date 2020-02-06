package com.rjdesenvolvimento.messagebroker.enums

enum class BatchSize(val value: Int) {
    Sixteen(16 * 1024),
    ThirtyTwo(32 * 1024),
    SixtyFour(64 * 1024),
    OneHundredAndTwentyEight(1024 * 1024)
}