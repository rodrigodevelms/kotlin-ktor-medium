package com.rjdesenvolvimento.accesses.validation

import am.ik.yavi.core.*
import java.time.*
import java.time.format.*
import java.util.*


class Message(
        val id: UUID,
        val date: String,
        val message: List<String>
)

fun erroMsg(T: ConstraintViolations): Message {
    val errorList = mutableListOf<String>()
    T.forEach { error -> errorList.add(error.message()) }
    return Message(
        UUID.randomUUID(),
        LocalDate.now().format(DateTimeFormatter.ISO_DATE),
        errorList
    )
}