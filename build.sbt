ThisBuild / organization := "com.example.copper"
ThisBuild / name := "test-assessment"
ThisBuild / scalaVersion := "2.12.10"
ThisBuild / version := "0.1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(
    mainClass := Some("co.copper.test.Application"),
    crossPaths := false,
    autoScalaLibrary := false,
    Defaults.itSettings,
    libraryDependencies ++= Seq(
      "com.sbuslab" % "utils" % "1.2.+",
      "com.sbuslab" % "akka-http-tools" % "1.1.+",
      "org.projectlombok" % "lombok" % "latest.release" % Provided,
      "org.scalatest" %% "scalatest" % "3.2.11" % "it,test",
      "org.mockito" %% "mockito-scala" % "1.17.5" % "it,test",
      "com.github.tomakehurst" % "wiremock" % "2.27.2" % "it,test",
      "org.json4s" %% "json4s-jackson" % "4.0.0",
      "org.json4s" %% "json4s-native" % "4.0.0"
    )
  )