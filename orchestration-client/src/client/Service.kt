package com.petshop.client

import com.petshop.producer.dispatch
import com.petshop.producer.producer
import com.rjdesenvolvimento.messagebroker.enums.Acks
import com.rjdesenvolvimento.messagebroker.enums.BatchSize
import com.rjdesenvolvimento.messagebroker.enums.CommandStatus
import com.rjdesenvolvimento.messagebroker.enums.Compression
import com.sksamuel.avro4k.Avro
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.*

class ClientService {

    suspend fun sendCommand(client: Client) {
        creatCommand(
            "create-client", client.id, CommandStatus.Open, client, "localhost:9092",
            true, Acks.All, Int.MAX_VALUE, 5, Compression.Snappy, 20,
            BatchSize.ThirtyTwo, "http://localhost:8081"
        )
    }

    suspend fun creatCommand(
        topicName: String,
        id: UUID,
        commandStatus: CommandStatus,
        message: Client,
        bootstrapServers: String,
        idempotence: Boolean,
        acks: Acks,
        retries: Int,
        requestPerConnection: Int,
        compression: Compression,
        linger: Int,
        batchSize: BatchSize,
        schemmaUrl: String
    ) {
        val producer = producer(
            bootstrapServers,
            idempotence,
            acks,
            retries,
            requestPerConnection,
            compression,
            linger,
            batchSize,
            schemmaUrl
        )

        Avro.default.schema(Command.serializer())
        val avroSchema = Avro.default.toRecord(Command.serializer(), Command(id, commandStatus, message))
        val record = ProducerRecord<String, GenericRecord>(topicName, id.toString(), avroSchema)
        coroutineScope { launch { producer.dispatch(record) } }
    }
}