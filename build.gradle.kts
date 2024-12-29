import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("fabric-loom")
    kotlin("jvm")
    kotlin("plugin.serialization")
}

val minecraftVersion: String by project

base {
    val archivesBaseName: String by project
    archivesName.set("$archivesBaseName-$minecraftVersion")
}

val modVersion: String by project
version = modVersion

val mavenGroup: String by project
group = mavenGroup

repositories {
    maven("https://api.modrinth.com/maven") {
        name = "Modrinth"
        content {
            includeGroup("maven.modrinth")
        }
    }

    maven("https://maven.nucleoid.xyz")
}

dependencies {
    minecraft("com.mojang", "minecraft", minecraftVersion)

    val yarnMappings: String by project
    mappings("net.fabricmc", "yarn", yarnMappings, null, "v2")

    val loaderVersion: String by project
    modImplementation("net.fabricmc", "fabric-loader", loaderVersion)

    val fabricVersion: String by project
    modImplementation("net.fabricmc.fabric-api", "fabric-api", fabricVersion)

    val fabricKotlinVersion: String by project
    modImplementation("net.fabricmc", "fabric-language-kotlin", fabricKotlinVersion)

    val ktorVersion: String by project
    include(implementation("io.ktor", "ktor-events-jvm", ktorVersion))
    include(implementation("io.ktor", "ktor-http-cio-jvm", ktorVersion))
    include(implementation("io.ktor", "ktor-http-jvm", ktorVersion))
    include(implementation("io.ktor", "ktor-io-jvm", ktorVersion))
    include(implementation("io.ktor", "ktor-network-jvm", ktorVersion))
    include(implementation("io.ktor", "ktor-server-cio-jvm", ktorVersion))
    include(implementation("io.ktor", "ktor-server-core-jvm", ktorVersion))
    include(implementation("io.ktor", "ktor-server-host-common-jvm", ktorVersion))
    include(implementation("io.ktor", "ktor-utils-jvm", ktorVersion))
    include(implementation("io.ktor", "ktor-server-html-builder-jvm", ktorVersion))

    include(implementation("org.jetbrains.kotlinx", "kotlinx-html-jvm", "0.9.1"))

    include(modImplementation("maven.modrinth", "ducky-updater-lib", "2024.10.2"))

    include(modImplementation("maven.modrinth", "fstats", "2023.12.3"))

    include(modImplementation("maven.modrinth", "modmenu-badges-lib", "2023.6.1"))
}

tasks {
    val javaVersion = JavaVersion.VERSION_21

    withType<JavaCompile> {
        options.encoding = "UTF-8"
        sourceCompatibility = javaVersion.toString()
        targetCompatibility = javaVersion.toString()
        options.release.set(javaVersion.toString().toInt())
    }

    withType<KotlinCompile> {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    jar {
        from("LICENSE")
    }

    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") { expand(mutableMapOf("version" to project.version)) }
    }


    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(javaVersion.toString()))
        }
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
        withSourcesJar()
    }
}
