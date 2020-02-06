package com.petshop.producer

import com.rjdesenvolvimento.messagebroker.enums.*
import io.confluent.kafka.serializers.*
import org.apache.avro.generic.*
import org.apache.kafka.clients.producer.*
import org.apache.kafka.clients.producer.ProducerConfig.*
import org.apache.kafka.common.serialization.*
import kotlin.collections.set
import kotlin.coroutines.*

fun producer(
    bootstrapServers: String,
    idempotence: Boolean,
    acks: Acks,
    retries: Int,
    requestPerConnection: Int,
    compression: Compression,
    linger: Int,
    batchSize: BatchSize,
    schemaUrl: String
): KafkaProducer<String, GenericRecord> {
    val prop: HashMap<String, Any> = HashMap()
    prop[BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
    prop[KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java.name
    prop[VALUE_SERIALIZER_CLASS_CONFIG] = KafkaAvroSerializer::class.java.name
    prop[ENABLE_IDEMPOTENCE_CONFIG] = idempotence.toString()
    prop[ACKS_CONFIG] = acks.value
    prop[RETRIES_CONFIG] = retries.toString()
    prop[MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION] = requestPerConnection.toString()
    prop[COMPRESSION_TYPE_CONFIG] = compression.value
    prop[LINGER_MS_CONFIG] = linger.toString()
    prop[BATCH_SIZE_CONFIG] = batchSize.value
    prop[SCHEMA_REGISTRY_URL] = schemaUrl

    return KafkaProducer(prop)
}

private const val SCHEMA_REGISTRY_URL = "schema.registry.url"

suspend inline fun <reified K : Any, reified V : Any> KafkaProducer<K, V>.dispatch(record: ProducerRecord<K, V>) =
    suspendCoroutine<RecordMetadata> { continuation ->
        val callback = Callback { metadata, exception ->
            if (metadata == null) {
                continuation.resumeWithException(exception!!)
            } else {
                continuation.resume(metadata)
            }
        }
        this.send(record, callback)
    }