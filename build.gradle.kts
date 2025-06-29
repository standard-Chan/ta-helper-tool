plugins {
    id("java")
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.4"
}

apply(plugin = "io.spring.dependency-management")


group = "com.teachAssistantHelper"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // lombok
    compileOnly ("org.projectlombok:lombok:1.18.36")
    annotationProcessor ("org.projectlombok:lombok:1.18.36")

    // spring boot
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-logging")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // spring  boot 유효성 검사
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // Spring Security
    implementation("org.springframework.boot:spring-boot-starter-security")

    // jwt
     implementation("io.jsonwebtoken:jjwt-api:0.11.5")
     runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
     runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // --- Mockito ---
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")

    // --- JSON 직렬화용 (ObjectMapper) ---
    testImplementation("com.fasterxml.jackson.core:jackson-databind")
//
//    // --- Spring Security 테스트
//    testImplementation("org.springframework.security:spring-security-test")

    //MYSQL
    implementation("mysql:mysql-connector-java:8.0.33")

    // Web socket
    implementation("org.springframework.boot:spring-boot-starter-websocket")

}

tasks.test {
    useJUnitPlatform()
}