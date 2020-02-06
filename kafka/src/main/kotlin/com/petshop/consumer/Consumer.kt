package com.petshop.consumer

import com.rjdesenvolvimento.messagebroker.enums.*
import org.apache.kafka.clients.consumer.*
import org.apache.kafka.clients.consumer.ConsumerConfig.*
import org.apache.kafka.common.serialization.*

fun consumer(
    bootstrapServers: String,
    group: String,
    autoCommit: Boolean,
    offsetBehaviour: OffsetBehaviour,
    pollMax: Int
): KafkaConsumer<String, Any> {
    val prop: HashMap<String, Any> = HashMap()
    prop[BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
    prop[KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
    prop[VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
    prop[GROUP_ID_CONFIG] = group
    prop[AUTO_OFFSET_RESET_CONFIG] = offsetBehaviour.value
    prop[ENABLE_AUTO_COMMIT_CONFIG] = autoCommit
    prop[MAX_POLL_RECORDS_CONFIG] = pollMax

    return KafkaConsumer(prop)
}