package com.petshop.commons

import java.util.*

interface IRepository<Model, ModelList, Entity> {
    suspend fun getById(id: UUID): Model?
    suspend fun insert(entity: Entity)
    suspend fun update(id: UUID, entity: Entity)
    suspend fun softDelete(id: UUID, activeInactive: Boolean)
}