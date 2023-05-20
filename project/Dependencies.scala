import sbt._

object Dependencies {
  val ZioVersion   = "2.0.10"
  val ZHTTPVersion = "3.0.0-RC1"

  val QuillVersion     = "4.6.0"
  val PostgresVersion  = "42.5.4"
  val LiquibaseVersion = "4.20.0"

  val ZioConfigVersion = "3.0.7"

  val LogbackVersion = "1.4.6"

  val ZioJsonVersion = "0.5.0"

  val ZioCryptoVersion = "0.0.0+238-70dbc3c7-SNAPSHOT"

  val `zio-http`      = "dev.zio" %% "zio-http" % ZHTTPVersion
  val `zio-http-test` = "dev.zio" %% "zio-http" % ZHTTPVersion % Test

  val quill     = "io.getquill"   %% "quill-jdbc-zio" % QuillVersion
  val postgres  = "org.postgresql" % "postgresql"     % PostgresVersion
  val liquibase = "org.liquibase"  % "liquibase-core" % LiquibaseVersion

  val `zio-config`: Seq[ModuleID] = Seq(
    "dev.zio" %% "zio-config"          % ZioConfigVersion,
    "dev.zio" %% "zio-config-magnolia" % ZioConfigVersion,
    "dev.zio" %% "zio-config-typesafe" % ZioConfigVersion
  )

  val logback = "ch.qos.logback" % "logback-classic" % LogbackVersion

  val `zio-json`: Seq[ModuleID] = Seq(
    "dev.zio" %% "zio-json"        % ZioJsonVersion,
    "dev.zio" %% "zio-json-macros" % ZioJsonVersion
  )

  val `zio-crypto` = "dev.zio" %% "zio-crypto" % ZioCryptoVersion

  val `zio-test`     = "dev.zio" %% "zio-test"     % ZioVersion % Test
  val `zio-test-sbt` = "dev.zio" %% "zio-test-sbt" % ZioVersion % Test
}
