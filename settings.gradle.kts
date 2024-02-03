rootProject.name = "confidentlyincorrect"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("spring-boot", "3.2.2")
            version("spring-framework", "6.1.3")

            library("spring-boot-starter-data-jpa", "org.springframework.boot", "spring-boot-starter-data-jpa").versionRef("spring-boot")
            library("spring-boot-starter-web", "org.springframework.boot", "spring-boot-starter-web").versionRef("spring-boot")
            library("spring-boot-starter-thymeleaf", "org.springframework.boot", "spring-boot-starter-thymeleaf").versionRef("spring-boot")
            library("spring-boot-starter-test", "org.springframework.boot", "spring-boot-starter-test").versionRef("spring-boot")
            library("spring-boot-devtools", "org.springframework.boot", "spring-boot-devtools").versionRef("spring-boot")
            library("spring-webmvc", "org.springframework", "spring-webmvc").versionRef("spring-framework")


            version("thymeleaf-layout-dialect", "3.3.0")
            version("jackson-module-kotlin", "2.16.1")
            version("kotlin-reflect", "1.9.22")
            version("kotlin-stdlib-jdk8", "1.9.22")
            version("mysql-connector-java", "8.0.33")

            library("thymeleaf-layout-dialect", "nz.net.ultraq.thymeleaf", "thymeleaf-layout-dialect").versionRef("thymeleaf-layout-dialect")
            library("jackson-module-kotlin", "com.fasterxml.jackson.module", "jackson-module-kotlin").versionRef("jackson-module-kotlin")
            library("jackson-annotations", "com.fasterxml.jackson.core", "jackson-annotations").versionRef("jackson-module-kotlin")
            library("jackson-databind", "com.fasterxml.jackson.core", "jackson-databind").versionRef("jackson-module-kotlin")
            library("kotlin-reflect", "org.jetbrains.kotlin", "kotlin-reflect").versionRef("kotlin-reflect")
            library("kotlin-stdlib-jdk8", "org.jetbrains.kotlin", "kotlin-stdlib-jdk8").versionRef("kotlin-stdlib-jdk8")
            library("mysql-connector-java", "mysql", "mysql-connector-java").versionRef("mysql-connector-java")
        }
    }
}
