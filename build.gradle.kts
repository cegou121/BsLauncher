plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.lwjgl.org") } // LWJGL репозиторий
}

dependencies {
    implementation("org.lwjgl:lwjgl:3.3.2:natives-arm64")
    implementation("org.lwjgl:lwjgl-glfw:3.3.2:natives-arm64")
    implementation("org.lwjgl:lwjgl-opengl:3.3.2:natives-arm64")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "org.example.Main" // Замените на свой основной класс
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}