package com.petshop.client

import com.petshop.commons.IRepository
import com.petshop.phone.PhoneTable
import com.petshop.settings.Netwok.dbQuery
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import java.util.*

class ClientService : IRepository<ClientModel, ClientsModel, Client> {
    suspend fun getAll(): List<ClientsModel> = dbQuery {
        ClientTable.selectAll().andWhere { (ClientTable.active eq true) }.map { toModelList(it) }
    }

    override suspend fun getById(id: UUID): ClientModel? = dbQuery {
        ClientTable.select { (ClientTable.id eq id) }.mapNotNull { toModelSingle(it) }.singleOrNull()
    }

    suspend fun getByName(name: String): List<ClientsModel> = dbQuery {
        ClientTable.selectAll().andWhere { (ClientTable.active eq true) }
            .andWhere { (ClientTable.name like "%${name}%") }.map { toModelList(it) }
    }

    override suspend fun insert(entity: Client): Unit = dbQuery {
        try {
            ClientTable.insert {
                it[id] = entity.id
                it[active] = entity.active
                it[name] = entity.name
                it[email] = entity.email
                it[document] = entity.document
                it[address] = entity.address
            }
            PhoneTable.batchInsert(entity.phones) { phone ->
                this[PhoneTable.id] = phone.id
                this[PhoneTable.active] = phone.active
                this[PhoneTable.number] = phone.number
                this[PhoneTable.clientFk] = entity.id
            }
        } catch (ex: ExposedSQLException) {
            ex.stackTrace
        }
    }

    override suspend fun update(id: UUID, entity: Client) {
        ClientTable.update({ ClientTable.id eq id }) {
            it[name] = entity.name
            it[email] = entity.email
            it[document] = entity.document
            it[address] = entity.address
        }
    }

    override suspend fun softDelete(id: UUID, activeInactive: Boolean): Unit = dbQuery {
        ClientTable.update({ ClientTable.id eq id }) { it[active] = !activeInactive }
    }
}