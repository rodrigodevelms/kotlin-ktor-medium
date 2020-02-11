val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val postgres_version: String by project
val hikari_version: String by project
val flyway_version: String by project
val exposed_version: String by project
val kafka_common_version: String by project
val commons_version: String by project

plugins {
    application
    kotlin("jvm") version "1.3.61"
    id("org.flywaydb.flyway") version "6.1.4"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.3.61"
}

group = "com.petshop"
version = "0.0.1"

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenLocal()
    jcenter()
    maven { url = uri("http://packages.confluent.io/maven/") }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("org.postgresql:postgresql:$postgres_version")
    implementation("com.zaxxer:HikariCP:$hikari_version")
    implementation("org.flywaydb:flyway-core:$flyway_version")
    implementation("org.jetbrains.exposed:exposed:$exposed_version")
    implementation("com.petshop:kafka:$kafka_common_version")
    implementation("com.petshop:commons:$commons_version")

    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")

flyway {
    url = System.getenv("URL")
    user = System.getenv("PSQL_USER")
    password = System.getenv("PSQL_PASSWORD")
    baselineOnMigrate = true
    locations = arrayOf("filesystem:resources/db/migration")
}