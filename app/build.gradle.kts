plugins {
    id("java")
    checkstyle
    id("org.sonarqube") version "7.2.3.7755"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

checkstyle {
    toolVersion = "10.17.0"
    configFile = file("config/checkstyle/checkstyle.xml")
    isIgnoreFailures = false
}
sonar {
    properties {
        property("sonar.projectKey", "AlekseyKurnakov_java-project-78")
        property("sonar.organization", "alekseykurnakov")
        property("sonar.projectName", "java-project-78")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}
