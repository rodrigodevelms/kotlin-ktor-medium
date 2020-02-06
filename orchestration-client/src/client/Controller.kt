package com.petshop.client

import com.rjdesenvolvimento.accesses.validation.erroMsg
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.route

fun Route.client(service: ClientService) {
    route("/api/orchestration") {
        post("/") {
            val client = call.receive<Client>()
            if (clientValidations(client)) call.respond(HttpStatusCode.Created, service.sendCommand(client))
            else call.respond(HttpStatusCode.BadRequest, erroMsg(validatorClient.validate(client)))
        }
    }
}