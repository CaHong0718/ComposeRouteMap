pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()

        //네이버 지도 연결
        maven {
            url = uri("https://repository.map.naver.com/archive/maven")
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        //네이버 지도 연결
        maven {
            url = uri("https://repository.map.naver.com/archive/maven")
        }
    }
}

rootProject.name = "ComposeRouteMap"
include(":app")
 