package com.alexrezv.personalfinances.dao.repositories.impl

import com.alexrezv.personalfinances.dao.entities.User
import com.alexrezv.personalfinances.dao.repositories.UserRepository
import io.getquill.SnakeCase
import io.getquill.jdbczio.Quill
import zio.{ZIO, ZLayer}

import java.sql.SQLException
import java.util.UUID

object UserRepositoryImpl {
  val layer: ZLayer[Quill.Postgres[SnakeCase], Nothing, UserRepositoryImpl] =
    ZLayer.fromFunction(UserRepositoryImpl.apply _)
}

final case class UserRepositoryImpl(quill: Quill.Postgres[SnakeCase]) extends UserRepository {
  import quill._

  implicit val userInsertMeta: quill.InsertMeta[User] = insertMeta[User](_.id)

  private val qs = quote {
    querySchema[User](
      entity = "usr",
      _.id       -> "id",
      _.uuid     -> "uuid",
      _.userName -> "username"
    )
  }

  override def getUsers: ZIO[Any, SQLException, List[User]] =
    run(qs)

  override def getUserByUUID(uuid: String): ZIO[Any, SQLException, Option[User]] =
    run(
      qs.filter(_.uuid == lift(UUID.fromString(uuid)))
    ).map(_.headOption)
}
