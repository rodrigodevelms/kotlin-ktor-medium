package com.petshop.client

import com.fasterxml.jackson.annotation.JsonProperty
import com.petshop.phone.Phone
import com.petshop.phone.PhoneTable
import com.petshop.phone.toPhone
import com.sksamuel.avro4k.serializer.UUIDSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.selectAll
import java.util.*

@Serializable()
data class Client(
    @Serializable(UUIDSerializer::class)
    val id: UUID,
    @JsonProperty("internalId")
    val internalId: Long?,
    val active: Boolean,
    val name: String,
    val email: String,
    val document: String,
    val phones: Collection<Phone>,
    @Serializable(UUIDSerializer::class)
    val address: UUID
)

object ClientTable : Table("users.client") {
    val id = ClientTable.uuid("id").primaryKey()
    val internalId = ClientTable.long("internal_id").autoIncrement()
    val active = ClientTable.bool("active")
    val name = ClientTable.varchar("name", 120)
    val email = ClientTable.varchar("email", 120).uniqueIndex()
    val document = ClientTable.varchar("document", 30).uniqueIndex()
    val address = ClientTable.uuid("address")
}

internal fun toClient(row: ResultRow): Client =
    Client(
        id = row[ClientTable.id],
        internalId = row[ClientTable.internalId],
        active = row[ClientTable.active],
        name = row[ClientTable.name],
        email = row[ClientTable.email],
        document = row[ClientTable.document],
        phones = PhoneTable.selectAll()
            .andWhere { PhoneTable.id eq row[ClientTable.id] }
            .andWhere { PhoneTable.active eq true }
            .map { toPhone(it) },
        address = row[ClientTable.address]
    )