rootProject.name = "confidentlyincorrect"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("depman", "1.1.7")
            plugin("depman", "io.spring.dependency-management").versionRef("depman")

            version("kotlin", "2.1.10")
            plugin("kotlin.jvm", "org.jetbrains.kotlin.jvm").versionRef("kotlin")
            plugin("kotlin.spring", "org.jetbrains.kotlin.plugin.spring").versionRef("kotlin")
            plugin("kotlin.jpa", "org.jetbrains.kotlin.plugin.jpa").versionRef("kotlin")

            version("spring-boot", "3.4.3")
            plugin("boot", "org.springframework.boot").versionRef("spring-boot")
            library("spring-boot-starter-data-jpa", "org.springframework.boot", "spring-boot-starter-data-jpa").versionRef("spring-boot")
            library("spring-boot-starter-web", "org.springframework.boot", "spring-boot-starter-web").versionRef("spring-boot")
            library("spring-boot-starter-thymeleaf", "org.springframework.boot", "spring-boot-starter-thymeleaf").versionRef("spring-boot")
            library("spring-boot-starter-test", "org.springframework.boot", "spring-boot-starter-test").versionRef("spring-boot")
            library("spring-boot-devtools", "org.springframework.boot", "spring-boot-devtools").versionRef("spring-boot")

            version("spring-framework", "6.2.4")
            library("spring-webmvc", "org.springframework", "spring-webmvc").versionRef("spring-framework")


            version("thymeleaf-layout-dialect", "3.4.0")
            library("thymeleaf-layout-dialect", "nz.net.ultraq.thymeleaf", "thymeleaf-layout-dialect").versionRef("thymeleaf-layout-dialect")

            version("jackson-module-kotlin", "2.18.3")
            library("jackson-module-kotlin", "com.fasterxml.jackson.module", "jackson-module-kotlin").versionRef("jackson-module-kotlin")
            library("jackson-annotations", "com.fasterxml.jackson.core", "jackson-annotations").versionRef("jackson-module-kotlin")
            library("jackson-databind", "com.fasterxml.jackson.core", "jackson-databind").versionRef("jackson-module-kotlin")

            version("kotlin-reflect", "2.1.10")
            library("kotlin-reflect", "org.jetbrains.kotlin", "kotlin-reflect").versionRef("kotlin-reflect")

            version("kotlin-stdlib-jdk8", "2.1.10")
            library("kotlin-stdlib-jdk8", "org.jetbrains.kotlin", "kotlin-stdlib-jdk8").versionRef("kotlin-stdlib-jdk8")

            version("mysql-connector-java", "8.0.33")
            library("mysql-connector-java", "mysql", "mysql-connector-java").versionRef("mysql-connector-java")

            version("kotlin.logging", "7.0.5")
            library("kotlin.logging", "io.github.oshai", "kotlin-logging-jvm").versionRef("kotlin.logging")
        }
    }
}
