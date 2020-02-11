package com.petshop.client

import org.jetbrains.exposed.sql.ResultRow
import java.util.*

data class ClientsModel(
    val id: UUID,
    val name: String,
    val email: String
)

data class ClientModel(
    val id: UUID,
    val name: String,
    val email: String,
    val document: String
)

internal fun toModelList(row: ResultRow): ClientsModel =
    ClientsModel(
        id = row[ClientTable.id],
        name = row[ClientTable.name],
        email = row[ClientTable.email]
    )

internal fun toModelSingle(row: ResultRow): ClientModel =
    ClientModel(
        id = row[ClientTable.id],
        name = row[ClientTable.name],
        email = row[ClientTable.email],
        document = row[ClientTable.document]
    )