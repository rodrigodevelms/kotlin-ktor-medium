
plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.61"
    `maven-publish`
}

group = "com.petshop"
version = "0.0.1"

repositories {
    mavenLocal()
    jcenter()
    maven { url = uri("http://packages.confluent.io/maven/") }
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
    implementation("org.apache.kafka:connect-json:2.4.0")
    implementation("io.confluent:kafka-avro-serializer:5.3.0")
    implementation("com.petshop:commons:0.0.1")

    api("org.apache.kafka:kafka-clients:2.4.0")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

java {
    withJavadocJar()
    withSourcesJar()
}


publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}