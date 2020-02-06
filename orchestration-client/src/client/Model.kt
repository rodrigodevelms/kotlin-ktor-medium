package com.petshop.client

import com.petshop.phone.Phone
import com.sksamuel.avro4k.serializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Client(
    @Serializable(UUIDSerializer::class)
    val id: UUID,
    val internalId: Long?,
    val active: Boolean,
    val name: String,
    val email: String,
    val document: String,
    val phones: Collection<Phone>,
    @Serializable(UUIDSerializer::class)
    val address: UUID
)
