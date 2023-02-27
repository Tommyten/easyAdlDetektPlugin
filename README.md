# EasyADL

This project is the home of the EasyADL architecture description language, the EasyADL Detekt plugin
and the EasyADL Gradle plugin.

When the EasyADL Detekt plugin is properly applied to the project you want to analyze with it,
simply execute the `detektMain` task and the EasyADL Detekt plugin will be run automatically,
generating the architecture report to the path given in its configuration.

## Setup

To make the EasyADL Detekt and Gradle plugins available for other projects to use, execute the
`publishToMavenLocal` command. This builds and packages the project, and publishes it to the local
Maven repository. To use one of the plugins in your own projects, add the `mavenLocal` repository
to your `settings.gradle` file in the `repositories` section of the `pluginManagement` block.
You also need to add the `mavenLocal` repository to the `build.gradle` in which you want to add
the EasyADL Detekt and Gradle plugins.

Both the Gradle and Detekt plugin also need some further configuration. The necessary configuration
is shown in the following example.

Example of applying the EasyADL Detekt and Gradle plugins using Kotlin script build and settings Gradle
files:

settings.gradle.kts
```
pluginManagement {
    repositories {
        mavenCentral()
        mavenLocal()
    }
}
```

build.gradle.kts
```
plugins {
    ...
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
    id("es.horm.easyadldetektplugin.gradleplugin") version "0.0.1"
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    ...
    detektPlugins("es.horm.easyadldetektplugin:easyAdlDetektPlugin:0.0.1")
}

easyAdl {
    // necessary so that the EasyADL Gradle plugin knows where the architecture description is
    archDescriptionPath = "pathToArchDescription\archDescription.eadl"
}

// This is needed so that Detekt properly generates the architecture report
tasks {
    withType<io.gitlab.arturbosch.detekt.Detekt> {
        reports {
            custom {
                reportId = "ArchReport"
                // This tells detekt, where it should write the report to,
                // you have to specify this file in the gitlab pipeline config.
                outputLocation.set(file("$buildDir/reports/easyAdl/archReport.html"))
            }
        }
    }
}
```

The `detekt.yml` file also needs some further configuration, so that the EasyADL Detekt plugin knows
where to find the architecture description file:

detekt.yml
```
...

EasyAdlRuleSet:
  EasyAdlComplianceRule:
    architectureDescriptionPath: 'pathToArchDescription\archDescription.eadl'
    active: true
```
