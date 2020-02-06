package com.petshop.client

import am.ik.yavi.builder.ValidatorBuilder
import am.ik.yavi.builder.forEach
import am.ik.yavi.builder.konstraint
import am.ik.yavi.core.Validator
import com.petshop.phone.Phone
import com.rjdesenvolvimento.accesses.validation.ValidateCPF
import com.rjdesenvolvimento.accesses.validation.ValidateEmail


val validatorClient: Validator<Client> = ValidatorBuilder.of(Client::class.java)
    .konstraint(Client::name) {
        notNull().message("The Name field is required")
        lessThan(121).message("The Name field must contain less than 120 characters")
        greaterThan(2).message("The Name field must contain more than 3 characters")
    }
    .konstraint(Client::email) {
        notBlank().message("The E-mail field is required")
        lessThan(121).message("The E-mail field must contain less than 120 characters")
        predicate(ValidateEmail.validate)
    }
    .konstraint(Client::document) {
        predicate(ValidateCPF.validate)
    }
    .forEach(Client::phones) {
        konstraint(Phone::number) {
            notBlank().message("The Phone Field is required")
        }
    }
    .build()

fun clientValidations(T: Client): Boolean {
    return validatorClient.validate(T).isValid
}
