plugins {
    id("java")
    id ("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "de.bluecolored.nbtlibtest"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://repo.codemc.io/repository/maven-public/")
}

dependencies {
    implementation (group = "net.kyori", name = "adventure-nbt", version = "4.11.0")
    implementation (group = "com.github.BlueMap-Minecraft", name = "BlueNBT", version = "v1.3.0")
    implementation (group = "com.github.Querz", name = "NBT", version = "6.1")
    implementation (group = "org.popcraft", name = "chunky-nbt", version = "1.3.131")

    compileOnly ("org.projectlombok:lombok:1.18.28")

    annotationProcessor ("org.projectlombok:lombok:1.18.28")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testCompileOnly ("org.projectlombok:lombok:1.18.28")
    testAnnotationProcessor ("org.projectlombok:lombok:1.18.28")
}

tasks.jar {
    manifest {
        attributes (
            "Main-Class" to "de.bluecolored.nbtlibtest.PerformanceTest"
        )
    }
}

tasks.test {
    useJUnitPlatform()
}