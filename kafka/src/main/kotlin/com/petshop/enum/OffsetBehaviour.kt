package com.rjdesenvolvimento.messagebroker.enums

enum class OffsetBehaviour(val value: String) {
    Latest("latest"),
    Earliest("earliest"),
    None("none")
}