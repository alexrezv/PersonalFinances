package com.alexrezv.personalfinances.dao.repositories

import com.alexrezv.personalfinances.dao.entities.User
import io.getquill.context.ZioJdbc.QIO
import zio.{&, ZIO}

import java.sql.SQLException
import javax.sql.DataSource

trait UserRepository {
  def getUsers: QIO[List[User]]

  def getUserByUUID(uuid: String): QIO[Option[User]]
}

object UserRepository {
  def getUsers: ZIO[DataSource & UserRepository, SQLException, List[User]] =
    ZIO.serviceWithZIO[UserRepository](_.getUsers)

  def getUserByUUID(uuid: String): ZIO[DataSource & UserRepository, SQLException, Option[User]] =
    ZIO.serviceWithZIO[UserRepository](_.getUserByUUID(uuid))
}
