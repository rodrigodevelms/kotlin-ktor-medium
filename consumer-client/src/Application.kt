package com.petshop

import com.petshop.client.ClientService
import com.petshop.client.consumerInsertClient
import com.petshop.settings.Netwok.startNetwokr
import io.ktor.application.Application
import kotlinx.coroutines.launch

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    startNetwokr()
    launch { consumerInsertClient(ClientService()) }
}

