plugins {
	java
	id("org.springframework.boot") version "3.4.5"
	id("io.spring.dependency-management") version "1.1.4"
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

ext["springCloudVersion"] = "2023.0.0"	// https://spring.io/projects/spring-cloud-stream

allprojects { // 루트프로젝트 + 하위프로젝트에 설정들이 적용됨
	group = "com.fc"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}
}

subprojects { // 하위프로젝트에만 적용
	apply {
		plugin("java")
		plugin("java-library")
		plugin("org.springframework.boot")
		plugin("io.spring.dependency-management")
	}

	java { // 자바설정은 하위프로젝트에도 해주는게 좋음
		toolchain {
			languageVersion = JavaLanguageVersion.of(21)
		}
	}

	dependencies {
		compileOnly("org.projectlombok:lombok:1.18.30") // 컴파일시에만 필요함
		annotationProcessor("org.projectlombok:lombok:1.18.30") // 롬복이 제공하는 어노테이션을 사용하겠다.

		testImplementation(platform("org.junit:junit-bom:5.10.0"))
		testImplementation("org.junit.jupiter:junit-jupiter")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
	}

	dependencyManagement {
		imports {
			mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
		}
	}

	tasks.test {
		useJUnitPlatform()
	}

}