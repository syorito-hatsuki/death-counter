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
    maven {
        name = "Modrinth"
        setUrl("https://api.modrinth.com/maven")
        content {
            includeGroup("maven.modrinth")
        }
    }

    maven {
        setUrl("https://maven.nucleoid.xyz")
    }

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

    include(implementation("org.jetbrains.kotlinx", "kotlinx-html-jvm", "0.8.0"))

    include(modImplementation("maven.modrinth", "ducky-updater-lib", "YvHo3R4h"))

    include(modImplementation("xyz.nucleoid", "server-translations-api", "2.0.0-beta.1+1.19.4-pre2"))
}

tasks {
    val javaVersion = JavaVersion.VERSION_17

    withType<JavaCompile> {
        options.encoding = "UTF-8"
        sourceCompatibility = javaVersion.toString()
        targetCompatibility = javaVersion.toString()
        options.release.set(javaVersion.toString().toInt())
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions { jvmTarget = javaVersion.toString() } }

    jar { from("LICENSE") { rename { "${it}_${base.archivesName}" } } }

    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") { expand(mutableMapOf("version" to project.version)) }
    }

    java {
        toolchain { languageVersion.set(JavaLanguageVersion.of(javaVersion.toString())) }
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
        withSourcesJar()
    }
}
