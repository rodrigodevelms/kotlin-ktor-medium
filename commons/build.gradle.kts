plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.61"
    `java-library`
}

repositories {
    mavenLocal()
    jcenter()
    maven { url = uri("http://packages.confluent.io/maven/") }
}

group = "com.petshop"
version = "0.0.1"

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    api("am.ik.yavi:yavi:0.2.7")
    api("com.fasterxml.jackson.datatype:jackson-datatype-joda:2.10.1")
    api("com.sksamuel.avro4k:avro4k-core:0.20.0")

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