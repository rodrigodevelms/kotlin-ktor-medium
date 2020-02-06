package com.rjdesenvolvimento.accesses.validation

import am.ik.yavi.core.*

enum class ValidateCPF : CustomConstraint<String?> {
    validate;

    override fun test(cpf: String?): Boolean {
        return validateCPF(cpf!!)
    }

    override fun messageKey(): String {
        return "CPF"
    }

    override fun defaultMessageFormat(): String {
        return "Invalid CPF number. Please inform a valid one"
    }
}

enum class ValidateEmail : CustomConstraint<String?> {
    validate;

    override fun test(email: String?): Boolean {
        return validateEmail(email!!)
    }

    override fun messageKey(): String {
        return "E-mail"
    }

    override fun defaultMessageFormat(): String {
        return "Invalid E-mail address. Please inform a valid one"
    }
}

enum class ValidatePhone : CustomConstraint<String?> {
    validate;

    override fun test(phone: String?): Boolean {
        return validatePhone(phone!!)
    }

    override fun messageKey(): String {
        return "Phone"
    }

    override fun defaultMessageFormat(): String {
        return "Invalid Phone number. Please inform a valid one"
    }
}