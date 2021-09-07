plugins {
    kotlin("jvm") version "1.5.21"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    `java-library`
}

repositories {
    mavenCentral() // 코드 한줄을 줄여주셨습니다 각별님 감사합니다
    maven("https://papermc.io/repo/repository/maven-public/") // PaperMC
    maven ("https://repo.aikar.co/content/groups/aikar/" ) //Akiar
}

dependencies {
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib:1.5.21") // Kotlin
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT") // Paper Latest
    compileOnly("io.github.monun:tap-api:4.1.6")
    compileOnly("io.github.monun:kommand-api:2.6.4") // 와 shadowJar 버리는 시대가 올 줄이야
    compileOnly("co.aikar:acf-paper:0.5.0-SNAPSHOT")
}

tasks { // 아니 시발 페이퍼 개놈들이 굳이 꼭 16만 되게 해야할 이유가 뭔데요
    processResources {
        filesMatching("**/*.yml") {
            expand(project.properties)
        }
        filteringCharset = "UTF-8"
    }
    shadowJar {
        archiveClassifier.set("dist")
        archiveVersion.set("")
        relocate("co.aikar.commands", "com.multiground.econ.acf")
        relocate ("co.aikar.locales", "com.multiground.econ.locales")
    }
    create<Copy>("dist") {
        from (shadowJar)
        into(".\\.server\\plugins")
    }
}
