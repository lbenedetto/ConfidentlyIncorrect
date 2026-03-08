rootProject.name = "confidentlyincorrect"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("depman", "1.1.7")
            plugin("depman", "io.spring.dependency-management").versionRef("depman")

            version("kotlin", "2.3.10")
            plugin("kotlin.jvm", "org.jetbrains.kotlin.jvm").versionRef("kotlin")
            plugin("kotlin.spring", "org.jetbrains.kotlin.plugin.spring").versionRef("kotlin")
            plugin("kotlin.jpa", "org.jetbrains.kotlin.plugin.jpa").versionRef("kotlin")
            library("kotlin-reflect", "org.jetbrains.kotlin", "kotlin-reflect").versionRef("kotlin")
            library("kotlin-stdlib-jdk8", "org.jetbrains.kotlin", "kotlin-stdlib-jdk8").versionRef("kotlin")

            version("spring-boot", "4.0.3")
            plugin("boot", "org.springframework.boot").versionRef("spring-boot")
            library("spring-boot-starter-data-jpa", "org.springframework.boot", "spring-boot-starter-data-jpa").versionRef("spring-boot")
            library("spring-boot-starter-web", "org.springframework.boot", "spring-boot-starter-web").versionRef("spring-boot")
            library("spring-boot-starter-thymeleaf", "org.springframework.boot", "spring-boot-starter-thymeleaf").versionRef("spring-boot")
            library("spring-boot-starter-test", "org.springframework.boot", "spring-boot-starter-test").versionRef("spring-boot")
            library("spring-boot-devtools", "org.springframework.boot", "spring-boot-devtools").versionRef("spring-boot")

            version("spring-framework", "7.0.5")
            library("spring-webmvc", "org.springframework", "spring-webmvc").versionRef("spring-framework")


            version("thymeleaf-layout-dialect", "4.0.0")
            library("thymeleaf-layout-dialect", "nz.net.ultraq.thymeleaf", "thymeleaf-layout-dialect").versionRef("thymeleaf-layout-dialect")

            version("jackson", "3.1.0")
            library("jackson-module-kotlin", "tools.jackson.module", "jackson-module-kotlin").versionRef("jackson")
            library("jackson-databind", "tools.jackson.core", "jackson-databind").versionRef("jackson")

            version("jackson-annotations", "2.21")
            library("jackson-annotations", "com.fasterxml.jackson.core", "jackson-annotations").versionRef("jackson-annotations")

            version("mysql-connector-java", "8.0.33")
            library("mysql-connector-java", "mysql", "mysql-connector-java").versionRef("mysql-connector-java")

            version("kotlin.logging", "8.0.01")
            library("kotlin.logging", "io.github.oshai", "kotlin-logging-jvm").versionRef("kotlin.logging")
        }
    }
}
