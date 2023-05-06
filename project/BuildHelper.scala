import sbt.{Def, _}
import sbt.Keys._
import scalafix.sbt.ScalafixPlugin.autoImport._

object BuildHelper {
  val ScalaVersion = "2.13.8"

  def commonSettings(scalaVersion: String): Seq[String] = CrossVersion.partialVersion(scalaVersion) match {
    case Some((3, _))                  => Seq.empty
    case Some((2, 12)) | Some((2, 13)) => Seq("-Ywarn-unused:params")
    case _                             => Seq.empty
  }
  def stdSettings: Seq[Def.Setting[_ >: Boolean with String with Task[Seq[String]] with Seq[sbt.ModuleID]]] = Seq(
    ThisBuild / fork              := true,
    ThisBuild / scalaVersion      := ScalaVersion,
    ThisBuild / scalacOptions     := commonSettings(scalaVersion.value),
    ThisBuild / semanticdbEnabled := true,
    ThisBuild / semanticdbVersion := scalafixSemanticdb.revision,
    ThisBuild / scalafixDependencies ++=
      List(
        "com.github.liancheng" %% "organize-imports" % "0.6.0"
      )
  )
}
