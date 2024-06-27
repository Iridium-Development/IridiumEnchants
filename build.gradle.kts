plugins {
    java
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.iridium"
version = "4.1.7"
description = "IridiumEnchants"

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://ci.ender.zone/plugin/repository/everything/")
    maven("https://jitpack.io")
    maven("https://nexus.iridiumdevelopment.net/repository/maven-releases/")
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.rosewooddev.io/repository/public/")
    maven("https://hub.jeff-media.com/nexus/repository/jeff-media-public/")
    maven("https://maven.enginehub.org/repo/")
    maven("https://repo.codemc.org/repository/maven-public/")
    maven("https://repo.bg-software.com/repository/api/")
    mavenCentral()
}

dependencies {
    // Dependencies that we want to shade in
    implementation("org.jetbrains:annotations:24.1.0")
    implementation("com.iridium:IridiumCore:1.9.5")
    implementation("org.bstats:bstats-bukkit:3.0.2")
    implementation("de.jeff_media:SpigotUpdateChecker:1.3.2")

    // Other dependencies that are not required or already available at runtime
    compileOnly("org.projectlombok:lombok:1.18.32")
    compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.6")
    compileOnly("com.iridium:IridiumSkyblock:4.0.9.1")
    compileOnly("com.massivecraft.massivesuper:MassiveCore:2.14.0")
    compileOnly("com.massivecraft.factions:Factions:3.3.0")
    compileOnly("com.massivecraft:Factions:1.6.9.5-4.1.4-STABLE") {
        exclude("com.darkblade12")
        exclude("org.kitteh")
    }
    compileOnly("com.wasteofplastic:askyblock:3.0.9.4")
    compileOnly("com.github.TownyAdvanced:Towny:0.100.3.3")
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.5-SNAPSHOT")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.2.6-SNAPSHOT")
    compileOnly("com.bgsoftware:SuperiorSkyblockAPI:1.11.1")
    compileOnly("com.github.angeschossen:LandsAPI:7.6.1")

    // Enable lombok annotation processing
    annotationProcessor("org.projectlombok:lombok:1.18.32")
}

tasks {
    // "Replace" the build task with the shadowJar task (probably bad but who cares)
    jar {
        dependsOn("shadowJar")
        enabled = false
    }

    shadowJar {
        fun relocate(origin: String) = relocate(origin, "com.iridium.iridiumenchants.dependencies${origin.substring(origin.lastIndexOf('.'))}")

        // Remove the archive classifier suffix
        archiveClassifier.set("")

        // Relocate dependencies
        relocate("com.j256.ormlite")
        relocate("org.bstats")
        relocate("de.jeff_media")

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
