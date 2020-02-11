package com.petshop.settings

import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.config.HoconApplicationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

object Netwok {
    private val appConfig = HoconApplicationConfig(ConfigFactory.load())
    private val dbUrl = appConfig.property("db.url").getString()
    private val dbDriver = appConfig.property("db.driver").getString()
    private val dbUser = appConfig.property("db.user").getString()
    private val dbPassword = appConfig.property("db.password").getString()

    fun startNetwokr() {
        Database.connect(hikari())
        val flyway = Flyway.configure().dataSource(
            dbUrl,
            dbUser,
            dbPassword
        ).load()
        flyway.migrate()
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.jdbcUrl = dbUrl
        config.driverClassName = dbDriver
        config.username = dbUser
        config.password = dbPassword
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }
}