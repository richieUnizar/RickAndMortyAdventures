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
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
}

rootProject.name = "RickAndMorty"
include(":app")
include(":data-source-rest")
include(":common")
include(":data")
include(":domain")
include(":presentation-character-list")
include(":presentation-character-details")
include(":presentation-base")
include(":data-source-room")
include(":presentation-favourite-characters")
