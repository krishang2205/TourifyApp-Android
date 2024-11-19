pluginManagement {
    repositories {
        google {
            content {
                // Specify that you only want to include dependencies for Android and Google libraries
                includeGroupByRegex("com\\.android.*") // For Android-specific dependencies
                includeGroupByRegex("com\\.google.*")  // For Google dependencies
                includeGroupByRegex("androidx.*")     // For AndroidX dependencies
            }
        }
        mavenCentral() // For general Java dependencies
        gradlePluginPortal() // For Gradle plugins
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS) // Ensure that only settings repositories are used
    repositories {
        google()    // Google repository
        mavenCentral()  // Maven Central repository
    }
}

rootProject.name = "tourify"
include(":app")  // Include the app module
