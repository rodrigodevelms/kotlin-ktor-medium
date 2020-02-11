package com.petshop.client

import com.petshop.consumer.consumer
import com.rjdesenvolvimento.messagebroker.enums.OffsetBehaviour
import com.sksamuel.avro4k.Avro
import org.apache.avro.generic.GenericRecord
import org.jetbrains.exposed.exceptions.*
import java.time.Duration

suspend fun consumerInsertClient(service: ClientService) {
    consumerCommand(
        "create-client", "localhost:9092", "consumer-client", false,
        OffsetBehaviour.Earliest, 10, 1000, service, "http://localhost:8081"
    )
}

suspend fun consumerCommand(
    topic: String,
    bootstrapServers: String,
    group: String,
    autoCommit: Boolean,
    offsetBehaviour: OffsetBehaviour,
    pollMax: Int,
    pollDuration: Long,
    service: ClientService,
    schemaUrl: String
) {
    val consumer = consumer(bootstrapServers, group, autoCommit, offsetBehaviour, pollMax, schemaUrl)
    consumer.subscribe(mutableListOf(topic))
    while (true) {
        val records = consumer.poll(Duration.ofMillis(pollDuration))
        if (records.count() > 0) {
            records.forEach {
                val client = it.value().get("message") as GenericRecord
                val entity = Avro.default.fromRecord(Client.serializer(), client)
                try {
                    service.insert(entity)
                } catch (ex: ExposedSQLException) {
                    ex.stackTrace
                }
            }
        }
    }
}