plugins {
    id("java-library")
    id("net.ltgt.apt-eclipse")
    id("net.ltgt.apt-idea")
}

applyPlatformAndCoreConfiguration()

repositories {
    maven {
        name = "spigot"
        url = uri("https://hub.spigotmc.org/nexus/content/groups/public")
    }
}

dependencies {
    "compile"(project(":worldguard-libs:core"))
    "compile"("com.sk89q.worldedit:worldedit-core:7.0.1-SNAPSHOT")
    "implementation"("org.flywaydb:flyway-core:3.0")
    "compile"("org.bukkit:bukkit:1.14.4-R0.1-SNAPSHOT")


    "compileOnly"("com.google.code.findbugs:jsr305:1.3.9")
    "testImplementation"("org.hamcrest:hamcrest-library:1.2.1")
}

tasks.withType<JavaCompile>().configureEach {
    dependsOn(":worldguard-libs:build")
}

sourceSets {
    main {
        java {
            srcDir("src/main/java")
        }
        resources {
            srcDir("src/main/resources")
        }
    }
}