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
    // Disallows project-level repositories and forces use of these settings-level repos.
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()  // Google's Maven repository for Android dependencies
        mavenCentral()  // Maven Central for other dependencies
    }
}

// Set the root project name
rootProject.name = "Job Applicaation Tracker"

// Include the app module
include(":app")
