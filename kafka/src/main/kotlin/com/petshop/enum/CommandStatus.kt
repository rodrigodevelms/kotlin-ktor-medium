package com.rjdesenvolvimento.messagebroker.enums

enum class CommandStatus(val value: String) {
    Open("Open"),
    Closed("Closed"),
    Processing("Processing"),
    Error("Error")
}