plugins {
    java
    `maven-publish`
    id("com.gradleup.shadow") version "9.3.1"
}

group = "com.iridium"
version = "4.1.9"
description = "IridiumEnchants"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://ci.ender.zone/plugin/repository/everything/")
    maven("https://jitpack.io")
    maven("https://nexus.iridiumdevelopment.net/repository/maven-releases/")
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.rosewooddev.io/repository/public/")
    maven("https://maven.enginehub.org/repo/")
    maven("https://repo.codemc.org/repository/maven-public/")
    maven("https://repo.bg-software.com/repository/api/")
}

dependencies {
    // Dependencies that we want to shade in
    implementation("org.jetbrains:annotations:26.1.0")
    implementation("com.iridium:IridiumCore:2.0.14")
    implementation("org.bstats:bstats-bukkit:3.1.0")
    implementation("com.jeff_media:SpigotUpdateChecker:3.0.4")

    // Other dependencies that are not required or already available at runtime
    compileOnly("org.projectlombok:lombok:1.18.42")
    compileOnly("org.spigotmc:spigot-api:1.21.1-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.12.2")
    compileOnly("com.iridium:IridiumSkyblock:4.1.2")
    compileOnly("com.massivecraft.massivesuper:MassiveCore:2.14.0")
    compileOnly("com.massivecraft.factions:Factions:3.3.0")
    compileOnly("com.massivecraft:Factions:1.6.9.5-4.1.4-STABLE") {
        exclude("com.darkblade12")
        exclude("org.kitteh")
    }
    compileOnly("com.wasteofplastic:askyblock:3.0.9.4")
    compileOnly("com.github.TownyAdvanced:Towny:0.102.0.7")
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.5-SNAPSHOT")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.2.6-SNAPSHOT")
    compileOnly("com.bgsoftware:SuperiorSkyblockAPI:2025.2.1")

    // Enable lombok annotation processing
    annotationProcessor("org.projectlombok:lombok:1.18.42")
}

tasks {
    // Add the shadowJar task to the build task
    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        fun relocate(origin: String) = relocate(origin, "com.iridium.iridiumenchants.dependencies${origin.substring(origin.lastIndexOf('.'))}")

        // Remove the archive classifier suffix
        archiveClassifier.set("")

        // Relocate dependencies
        relocate("com.iridium.iridiumcolorapi")
        relocate("com.iridium.iridiumcore")
        relocate("com.j256.ormlite")
        relocate("com.jeff_media.updatechecker")
        relocate("org.bstats")

        // Relocate IridiumCore dependencies
        relocate("com.cryptomorin.xseries")
        relocate("com.fasterxml.jackson")
        relocate("de.tr7zw.changeme.nbtapi")
        relocate("io.papermc")
        relocate("org.apache.commons")
        relocate("org.intellij")
        relocate("org.jetbrains")
        relocate("org.yaml.snakeyaml")

        // Remove unnecessary files from the jar
        minimize()
    }

    // Set UTF-8 as the encoding
    compileJava {
        options.encoding = "UTF-8"
    }

    // Process Placeholders for the plugin.yml
    processResources {
        filesMatching("**/plugin.yml") {
            expand(rootProject.project.properties)
        }

        // Always re-run this task
        outputs.upToDateWhen { false }
    }

    compileJava {
        sourceCompatibility = JavaVersion.VERSION_1_8.toString()
        targetCompatibility = JavaVersion.VERSION_1_8.toString()
    }

    compileTestJava {
        sourceCompatibility = JavaVersion.VERSION_17.toString()
        targetCompatibility = JavaVersion.VERSION_17.toString()
    }
}

// Maven publishing
publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}
