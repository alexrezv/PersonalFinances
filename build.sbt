import Dependencies._

// give the user a nice default project!
ThisBuild / organization := "com.alexrezv"
ThisBuild / version := "1.0.0"

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(BuildHelper.stdSettings)
  .settings(
    name := "PersonalFinances",
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
    libraryDependencies ++= Seq(
      `zio-test`, `zio-test-sbt`, `zio-http`, `zio-http-test`,
      quill, postgres, liquibase, logback,
      `zio-crypto`
    ) ++ `zio-config`++ `zio-json`,
    scalacOptions ++= Seq(
      "-Ymacro-annotations"
    ),
    resolvers += "Sonatype_Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
  )
  .settings(
    Docker / version          := version.value,
    Compile / run / mainClass := Option("com.alexrezv.personalfinances.PersonalFinances")
  )

addCommandAlias("fmt", "scalafmt; Test / scalafmt; sFix;")
addCommandAlias("fmtCheck", "scalafmtCheck; Test / scalafmtCheck; sFixCheck")
addCommandAlias("sFix", "scalafix OrganizeImports; Test / scalafix OrganizeImports")
addCommandAlias("sFixCheck", "scalafix --check OrganizeImports; Test / scalafix --check OrganizeImports")
