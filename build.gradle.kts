import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    `maven-publish`
    antlr
    id("io.gitlab.arturbosch.detekt").version("1.22.0")
    id("java-gradle-plugin")
}

group = "es.horm.easyadldetektplugin"
version = "0.0.1"

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven") }
}

dependencies {
    api("io.gitlab.arturbosch.detekt:detekt-api:1.22.0")

    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.8.0")
    implementation("com.yuvalshavit:antlr-denter:1.1")
    antlr("org.antlr:antlr4:4.11.1")

    testImplementation("io.gitlab.arturbosch.detekt:detekt-test:1.22.0")
    testImplementation("io.kotest:kotest-assertions-core:5.6.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
}

tasks.withType<JavaCompile>() {
    targetCompatibility = "11"
    sourceCompatibility = "11"
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    systemProperty("junit.jupiter.testinstance.lifecycle.default", "per_class")
    systemProperty("compile-snippet-tests", project.hasProperty("compile-test-snippets"))
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}

detekt {
    toolVersion = "1.22.0"
    config = files("config/detekt/detekt.yml")
    buildUponDefaultConfig = true
}

gradlePlugin {
    plugins {
        create("easyAdlPlugin") {
            id = "es.horm.easyadldetektplugin.gradleplugin"
            implementationClass = "es.horm.easyadldetektplugin.gradleplugin.EasyAdlPlugin"
        }
    }
}
