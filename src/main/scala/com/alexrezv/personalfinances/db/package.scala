package com.alexrezv.personalfinances

import io.getquill._
import io.getquill.jdbczio.Quill
import zio._

import javax.sql.DataSource

package object db {
  var quillLayer: ZLayer[DataSource, Nothing, Quill.Postgres[SnakeCase.type]] =
    Quill.Postgres.fromNamingStrategy(SnakeCase)

  val dsLayer: ZLayer[Any, Throwable, DataSource] =
    Quill.DataSource.fromPrefix("db")
}
