pluginManagement {
    repositories {
        gradlePluginPortal()
        // 阿里云云效仓库（Gradle 插件）：https://maven.aliyun.com/mvn/guide
        maven(url = uri("https://maven.aliyun.com/repository/gradle-plugin"))
        // 阿里云云效仓库：https://maven.aliyun.com/mvn/guide
        maven(url = uri("https://maven.aliyun.com/repository/public"))
        maven(url = uri("https://maven.aliyun.com/repository/google"))
        // 华为开源镜像：https://mirrors.huaweicloud.com
        maven(url = uri("https://repo.huaweicloud.com/repository/maven"))
        // JitPack 远程仓库：https://jitpack.io
        maven(url = uri("https://jitpack.io"))
        // MavenCentral 远程仓库：https://mvnrepository.com
        mavenCentral()
        // Google 仓库：https://maven.google.com/web/index.html
        google()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // 阿里云云效仓库：https://maven.aliyun.com/mvn/guide
        maven(url = uri("https://maven.aliyun.com/repository/public"))
        maven(url = uri("https://maven.aliyun.com/repository/google"))
        // 华为开源镜像：https://mirrors.huaweicloud.com
        maven(url = uri("https://repo.huaweicloud.com/repository/maven"))
        // JitPack 远程仓库：https://jitpack.io
        maven(url = uri("https://jitpack.io"))
        // MavenCentral 远程仓库：https://mvnrepository.com
        mavenCentral()
        // Google 仓库：https://maven.google.com/web/index.html
        google()
    }
}

rootProject.name = "mywanandroid"
include(":app")
