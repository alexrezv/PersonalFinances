package com.alexrezv.personalfinances.dao.repositories.impl

import com.alexrezv.personalfinances.dao.entities.User
import com.alexrezv.personalfinances.dao.repositories.UserRepository
import com.alexrezv.personalfinances.db
import io.getquill.context.ZioJdbc.QIO
import zio.{ULayer, ZLayer}

import java.util.UUID

object UserRepositoryImpl {
  val layer: ULayer[UserRepositoryImpl] =
    ZLayer.succeed(new UserRepositoryImpl())
}

final case class UserRepositoryImpl() extends UserRepository {
  import db.Ctx._

  private val qs = quote {
    querySchema[User](
      entity = "usr",
      _.id       -> "id",
      _.uuid     -> "uuid",
      _.userName -> "username",
      _.password -> "password"
    )
  }

  override def list: QIO[List[User]] =
    run(qs)

  override def findByUUID(uuid: String): QIO[Option[User]] =
    run(
      qs.filter(_.uuid == lift(UUID.fromString(uuid)))
    ).map(_.headOption)

  override def findByLogin(userName: String): QIO[Option[User]] =
    run(
      qs.filter(_.userName == lift(userName))
    ).map(_.headOption)

  override def insert(user: User): QIO[Unit] =
    run(
      qs.insert(
        _.uuid     -> lift(user.uuid),
        _.userName -> lift(user.userName),
        _.password -> lift(user.password)
      )
    ).unit
}
