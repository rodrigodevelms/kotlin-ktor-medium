package com.rjdesenvolvimento.accesses.validation

import java.util.regex.*

fun validatePhone(phone: String): Boolean {
    return Pattern.compile("^(\\+[1-9]?[1-9]?[1-9]) ([1-9]?[1-9]?[1-9][1-9]) (?:[2-8]|9[1-9])[0-9]{3}" +
            " [0-9]{4}$").matcher(phone).matches()
}