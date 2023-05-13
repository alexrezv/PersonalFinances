package com.alexrezv.personalfinances.dao.repositories

import com.alexrezv.personalfinances.dao.entities.User
import zio.ZIO

import java.sql.SQLException

trait UserRepository {
  def getUsers: ZIO[Any, SQLException, List[User]]

  def getUserByUUID(uuid: String): ZIO[Any, SQLException, Option[User]]
}

object UserRepository {
  def getUsers: ZIO[UserRepository, SQLException, List[User]] =
    ZIO.serviceWithZIO[UserRepository](_.getUsers)

  def getUserByUUID(uuid: String): ZIO[UserRepository, SQLException, Option[User]] =
    ZIO.serviceWithZIO[UserRepository](_.getUserByUUID(uuid))
}
