
dependencies {
    // core 모듈 추가
    implementation(project(mapOf("path" to ":core")))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-stream-kafka")
    implementation("org.springframework.cloud:spring-cloud-starter-function-web") // 추가
}

