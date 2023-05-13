import sbt._

object Dependencies {
  val ZioVersion   = "2.0.10"
  val ZHTTPVersion = "3.0.0-RC1"

  val QuillVersion = "4.6.0"
  val PostgresVersion = "42.5.4"
  val LiquibaseVersion = "4.20.0"

  val ZioConfigVersion = "3.0.7"

  val LogbackVersion = "1.4.6"


  val `zio-http`      = "dev.zio" %% "zio-http" % ZHTTPVersion
  val `zio-http-test` = "dev.zio" %% "zio-http" % ZHTTPVersion % Test

  val `quill`     = "io.getquill" %% "quill-jdbc-zio" % QuillVersion
  val `postgres`  = "org.postgresql" % "postgresql" % PostgresVersion
  val `liquibase` = "org.liquibase" % "liquibase-core" % LiquibaseVersion

  val `zio-config`          = "dev.zio" %% "zio-config" % ZioConfigVersion
  val `zio-config-magnolia` = "dev.zio" %% "zio-config-magnolia" % ZioConfigVersion
  val `zio-config-typesafe` = "dev.zio" %% "zio-config-typesafe" % ZioConfigVersion

  val `logback` = "ch.qos.logback" % "logback-classic" % LogbackVersion

  val `zio-test`     = "dev.zio" %% "zio-test"     % ZioVersion % Test
  val `zio-test-sbt` = "dev.zio" %% "zio-test-sbt" % ZioVersion % Test
}
