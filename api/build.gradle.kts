

dependencies {
    // core 모듈 추가
    implementation(project(mapOf("path" to ":core")))
    implementation("org.springframework.boot:spring-boot-starter-web")
}

