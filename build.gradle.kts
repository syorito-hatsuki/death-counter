plugins {
    id("fabric-loom")
    kotlin("jvm")
    kotlin("plugin.serialization")
}

base {
    val archivesBaseName: String by project
    archivesName.set(archivesBaseName)
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
}

dependencies {
    implementation("io.ktor:ktor-server-cors-jvm:2.1.2")
    val minecraftVersion: String by project
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
    include(implementation("io.ktor:ktor-events-jvm:$ktorVersion")!!)
    include(implementation("io.ktor:ktor-http-cio-jvm:$ktorVersion")!!)
    include(implementation("io.ktor:ktor-http-jvm:$ktorVersion")!!)
    include(implementation("io.ktor:ktor-io-jvm:$ktorVersion")!!)
    include(implementation("io.ktor:ktor-network-jvm:$ktorVersion")!!)
    include(implementation("io.ktor:ktor-serialization-jvm:$ktorVersion")!!)
    include(implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")!!)
    include(implementation("io.ktor:ktor-serialization-kotlinx-jvm:$ktorVersion")!!)
    include(implementation("io.ktor:ktor-server-cio-jvm:$ktorVersion")!!)
    include(implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")!!)
    include(implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")!!)
    include(implementation("io.ktor:ktor-server-cors-jvm:$ktorVersion")!!)
    include(implementation("io.ktor:ktor-server-freemarker-jvm:$ktorVersion")!!)
    include(implementation("io.ktor:ktor-server-host-common-jvm:$ktorVersion")!!)
    include(implementation("io.ktor:ktor-utils-jvm:$ktorVersion")!!)
    include(implementation("io.ktor:ktor-websockets-jvm:$ktorVersion")!!)
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
