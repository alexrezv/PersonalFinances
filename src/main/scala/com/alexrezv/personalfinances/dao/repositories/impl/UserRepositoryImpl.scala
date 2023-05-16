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

  implicit val userInsertMeta: InsertMeta[User] = insertMeta[User](_.id)

  private val qs = quote {
    querySchema[User](
      entity = "usr",
      _.id       -> "id",
      _.uuid     -> "uuid",
      _.userName -> "username"
    )
  }

  override def getUsers: QIO[List[User]] =
    run(qs)

  override def getUserByUUID(uuid: String): QIO[Option[User]] =
    run(
      qs.filter(_.uuid == lift(UUID.fromString(uuid)))
    ).map(_.headOption)
}
