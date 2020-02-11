package com.petshop.phone

import com.fasterxml.jackson.annotation.JsonProperty
import com.petshop.client.ClientTable
import com.sksamuel.avro4k.serializer.UUIDSerializer
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import java.util.*

@Serializable
data class Phone(
    @Serializable(UUIDSerializer::class)
    @JsonProperty("id")
    val id: UUID,
    @JsonProperty("internalId")
    val internalId: Long?,
    @JsonProperty("active")
    val active: Boolean,
    @JsonProperty("number")
    val number: String,
    @Serializable(UUIDSerializer::class)
    @JsonProperty("clientFk")
    val clientFk: UUID?
)

object PhoneTable : Table("phones.phone") {
    val id = uuid("id").primaryKey()
    val internalId = long("internal_id").autoIncrement()
    val active = bool("active")
    val number = varchar("number", 20)
    val clientFk = reference("client_fk", ClientTable.id, ReferenceOption.CASCADE)
}

internal fun toPhone(row: ResultRow): Phone =
    Phone(
        id = row[PhoneTable.id],
        internalId = row[PhoneTable.internalId],
        active = row[PhoneTable.active],
        number = row[PhoneTable.number],
        clientFk = row[PhoneTable.clientFk]
    )