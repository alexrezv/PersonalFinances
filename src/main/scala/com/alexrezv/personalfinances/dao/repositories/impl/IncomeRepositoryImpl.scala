package com.alexrezv.personalfinances.dao.repositories.impl

import com.alexrezv.personalfinances.dao.entities.{IncomeCategory, IncomeRecord}
import com.alexrezv.personalfinances.dao.repositories.IncomeRepository
import com.alexrezv.personalfinances.db
import io.getquill.context.ZioJdbc.QIO
import zio.{ULayer, ZLayer}

import java.sql.Types
import java.util.UUID

object IncomeRepositoryImpl {
  val layer: ULayer[IncomeRepositoryImpl] =
    ZLayer.succeed(new IncomeRepositoryImpl())
}

final case class IncomeRepositoryImpl() extends IncomeRepository {
  import db.Ctx._

  implicit val incomeInsertMeta: InsertMeta[IncomeRecord] = insertMeta[IncomeRecord](_.id)

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

  override def getIncomeRecordsByUserId(uuid: String): QIO[List[IncomeRecord]] =
    run(
      qs.filter(_.usrId == lift(UUID.fromString(uuid)))
    )
}
