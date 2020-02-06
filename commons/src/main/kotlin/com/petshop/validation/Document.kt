package com.rjdesenvolvimento.accesses.validation

fun validateCPF(cpf: String): Boolean {
    val numbers = arrayListOf<Int>()

    cpf.filter { it.isDigit() }.forEach {
        numbers.add(it.toString().toInt())
    }

    if (numbers.size != 11) return false

    verifyEachNumber(numbers, 10)

    //digito 1
    val dv1 = ((0..8).sumBy { (it + 1) * numbers[it] }).rem(11).let {
        if (it >= 10) 0 else it
    }

    val dv2 = ((0..8).sumBy { it * numbers[it] }.let { (it + (dv1 * 9)).rem(11) }).let {
        if (it >= 10) 0 else it
    }
    return numbers[9] == dv1 && numbers[10] == dv2
}

fun validateCNPJ(cnpj: String): Boolean {
    if (cnpj.isBlank()) return false

    val numbers = arrayListOf<Int>()
    cnpj.filter { it.isDigit() }.forEach {
        numbers.add(it.toString().toInt())
    }
    if (numbers.size != 14) return false

    verifyEachNumber(numbers, 14)

    //digito 1
    val dv1 = 11 - (arrayOf(5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2).mapIndexed { index, i ->
        i * numbers[index]
    }).sum().rem(11)
    numbers.add(dv1)

    //digito 2
    val dv2 = 11 - (arrayOf(6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2).mapIndexed { index, i ->
        i * numbers[index]
    }).sum().rem(11)

    return numbers[12] == dv1 && numbers[13] == dv2
}

private fun verifyEachNumber(numbers: List<Int>, number: Int): Boolean {
    (0..9).forEach { n ->
        val digits = arrayListOf<Int>()
        (0..number).forEach { digits.add(n) }
        if (numbers == digits) return false
    }
    return true
}