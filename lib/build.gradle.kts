plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.7.0"

    // Apply the java-library plugin for API and implementation separation.
    `java-library`
    `maven-publish`
    signing
}

publishing {
    repositories {
        maven {
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2")
            credentials {
                username = System.getenv("OSSRH_USERNAME")
                password = System.getenv("OSSRH_TOKEN")
            }
        }
    }

    publications {
        create<MavenPublication>("mavenJava") {
            pom {
                from(components["java"])
                groupId = "io.github.ferhatwi"
                artifactId = "supabase-storage-kt"
                version = "0.1.2"
                name.set("Supabase Storage")
                description.set("Kotlin client for Supabase Storage")
                url.set("http://www.github.com/ferhatwi/supabase-storage-kt")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("ferhatwi")
                        name.set("Ferhat")
                        email.set("ferhatyigit7@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/ferhatwi/supabase-storage-kt.git")
                    developerConnection.set("scm:git:ssh://github.com/ferhatwi/ferhatwi.git")
                    url.set("http://github.com/ferhatwi/supabase-storage-kt")
                }
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}

java {
    withJavadocJar()
    withSourcesJar()
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    api("io.github.ferhatwi:supabase-kt:0.2.5")

}