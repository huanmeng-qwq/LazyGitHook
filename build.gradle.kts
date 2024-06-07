plugins {
    kotlin("jvm") version "1.9.23"
    id("io.ktor.plugin") version "2.3.10"
}

group = "com.huanmeng-qwq"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application{
    mainClass = "me.huanmeng.lazygithook.MainKt"
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("cn.hutool:hutool-core:5.8.25")
    // lazykook
    implementation(fileTree("libs"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}