dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.testcontainers:testcontainers:1.20.1")
    api("org.springframework.cloud:spring-cloud-starter-stream-kafka")
}