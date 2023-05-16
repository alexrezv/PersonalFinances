package com.alexrezv.personalfinances.dao.repositories.impl

import com.alexrezv.personalfinances.dao.entities.{SpendingCategory, SpendingRecord}
import com.alexrezv.personalfinances.dao.repositories.SpendingRepository
import com.alexrezv.personalfinances.db
import io.getquill.context.ZioJdbc.QIO
import zio.{ULayer, ZLayer}

import java.sql.Types
import java.util.UUID

object SpendingRepositoryImpl {
  val layer: ULayer[SpendingRepositoryImpl] =
    ZLayer.succeed(new SpendingRepositoryImpl())
}

final case class SpendingRepositoryImpl() extends SpendingRepository {
  import db.Ctx._

  implicit val incomeInsertMeta: InsertMeta[SpendingRecord] = insertMeta[SpendingRecord](_.id)

  private val qs = quote {
    querySchema[SpendingRecord](
      entity = "spending",
      _.id       -> "id",
      _.usrId    -> "usr_id",
      _.date     -> "date",
      _.amount   -> "amount",
      _.category -> "category",
      _.comment  -> "comment"
    )
  }

  implicit val categoryDecoder: Decoder[SpendingCategory] = decoder { (index, resultRow, _) =>
    val category = resultRow.getObject(index).toString
    SpendingCategory.fromString(category).get
  }

  implicit val categoryEncoder: Encoder[SpendingCategory] = encoder(
    sqlType = Types.OTHER,
    (index, category, row) => row.setObject(index, category.name)
  )

  override def getSpendingRecordsByUserId(
      uuid: String
    ): QIO[List[SpendingRecord]] =
    run(
      qs.filter(_.usrId == lift(UUID.fromString(uuid)))
    )
}
