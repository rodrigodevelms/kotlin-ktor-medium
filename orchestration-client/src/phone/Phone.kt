package com.petshop.phone

import com.sksamuel.avro4k.serializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Phone(
    @Serializable(UUIDSerializer::class)
    val id: UUID,
    val internalId: Long?,
    val active: Boolean,
    val number: String
)