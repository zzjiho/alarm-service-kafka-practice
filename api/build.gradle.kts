// api 모듈은 mongo를 쓰는지 redis를 쓰는지 관심 없음 core에서 가지고 있게함. (정답X)
dependencies {
    implementation(project(mapOf("path" to ":core")))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-common:2.2.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
}