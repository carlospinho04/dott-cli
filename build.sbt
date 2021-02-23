import Dependencies._

lazy val root = (project in file("."))
  .settings(
    organization:= "com.carlos",
    name := "dott-cli",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.4",
    libraryDependencies ++= cliDependencies,
    testFrameworks += new TestFramework("munit.Framework")
  )
