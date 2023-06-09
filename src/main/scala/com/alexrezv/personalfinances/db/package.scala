package com.alexrezv.personalfinances

import io.getquill._
import io.getquill.jdbczio.Quill
import zio._

import javax.sql.DataSource

package object db {
  object Ctx extends PostgresZioJdbcContext(NamingStrategy(SnakeCase))

  val dsLayer: ZLayer[Any, Throwable, DataSource] =
    Quill.DataSource.fromPrefix("db")
}
