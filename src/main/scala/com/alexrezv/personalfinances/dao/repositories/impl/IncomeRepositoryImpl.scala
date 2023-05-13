package com.alexrezv.personalfinances.dao.repositories.impl

import com.alexrezv.personalfinances.dao.entities.{IncomeCategory, IncomeRecord}
import com.alexrezv.personalfinances.dao.repositories.IncomeRepository
import io.getquill.SnakeCase
import io.getquill.jdbczio.Quill
import zio.{ZIO, ZLayer}

import java.sql.{SQLException, Types}
import java.util.UUID

object IncomeRepositoryImpl {
  val layer: ZLayer[Quill.Postgres[SnakeCase], Nothing, IncomeRepositoryImpl] =
    ZLayer.fromFunction(IncomeRepositoryImpl.apply _)
}

final case class IncomeRepositoryImpl(quill: Quill.Postgres[SnakeCase]) extends IncomeRepository {
  import quill._

  implicit val incomeInsertMeta: quill.InsertMeta[IncomeRecord] = insertMeta[IncomeRecord](_.id)

  private val qs = quote {
    querySchema[IncomeRecord](
      entity = "income",
      _.id       -> "id",
      _.usrId    -> "usr_id",
      _.date     -> "date",
      _.amount   -> "amount",
      _.category -> "category",
      _.comment  -> "comment"
    )
  }

  implicit val categoryDecoder: Decoder[IncomeCategory] = decoder { (index, resultRow, _) =>
    val category = resultRow.getObject(index).toString
    IncomeCategory.fromString(category).get
  }

  implicit val categoryEncoder: Encoder[IncomeCategory] = encoder(
    sqlType = Types.OTHER,
    (index, category, row) => row.setObject(index, category.name)
  )

  override def getIncomeRecordsByUserId(uuid: String): ZIO[Any, SQLException, List[IncomeRecord]] =
    run(
      qs.filter(_.usrId == lift(UUID.fromString(uuid)))
    )
}
