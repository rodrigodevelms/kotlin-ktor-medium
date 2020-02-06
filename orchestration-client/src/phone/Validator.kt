package com.rjdesenvolvimento.accesses.orchestration.common.phone

import am.ik.yavi.builder.ValidatorBuilder
import am.ik.yavi.builder.konstraint
import am.ik.yavi.core.Validator
import com.petshop.phone.Phone
import com.rjdesenvolvimento.accesses.validation.ValidatePhone

val validatorPhoness: Validator<Phone> = ValidatorBuilder.of<Phone>()
    .konstraint(Phone::number) {
        notBlank().message("The Phone field is required")
        predicate(ValidatePhone.validate)
    }.build()

fun phoneValidations(T: Phone): Boolean {
    return validatorPhoness.validate(T).isValid
}