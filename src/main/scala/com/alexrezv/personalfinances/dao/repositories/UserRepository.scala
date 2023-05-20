package com.alexrezv.personalfinances.dao.repositories

import com.alexrezv.personalfinances.dao.entities.User
import io.getquill.context.ZioJdbc.QIO
import zio.{&, ZIO}

import java.sql.SQLException
import javax.sql.DataSource

trait UserRepository {
  def list: QIO[List[User]]

  def findByUUID(uuid: String): QIO[Option[User]]

  def findByLogin(userName: String): QIO[Option[User]]

  def insert(user: User): QIO[Unit]
}

object UserRepository {
  def list: ZIO[DataSource & UserRepository, SQLException, List[User]] =
    ZIO.serviceWithZIO[UserRepository](_.list)

  def findByUUID(uuid: String): ZIO[DataSource & UserRepository, SQLException, Option[User]] =
    ZIO.serviceWithZIO[UserRepository](_.findByUUID(uuid))

  def findByLogin(userName: String): ZIO[DataSource with UserRepository, SQLException, Option[User]] =
    ZIO.serviceWithZIO[UserRepository](_.findByLogin(userName))

  def insert(user: User): ZIO[DataSource & UserRepository, SQLException, Unit] =
    ZIO.serviceWithZIO[UserRepository](_.insert(user))
}
