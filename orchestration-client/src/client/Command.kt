package com.petshop.client

import com.rjdesenvolvimento.messagebroker.enums.CommandStatus
import com.sksamuel.avro4k.serializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Command(
    @Serializable(UUIDSerializer::class)
    val id: UUID,
    val status: CommandStatus,
    val message: Client
)