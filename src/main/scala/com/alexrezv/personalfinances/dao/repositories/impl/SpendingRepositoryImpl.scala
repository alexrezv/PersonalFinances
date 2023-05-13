package com.alexrezv.personalfinances.dao.repositories.impl

import com.alexrezv.personalfinances.dao.entities.{SpendingCategory, SpendingRecord}
import com.alexrezv.personalfinances.dao.repositories.SpendingRepository
import io.getquill.SnakeCase
import io.getquill.jdbczio.Quill
import zio.{ZIO, ZLayer}

import java.sql.{SQLException, Types}
import java.util.UUID

object SpendingRepositoryImpl {
  val layer: ZLayer[Quill.Postgres[SnakeCase], Nothing, SpendingRepositoryImpl] =
    ZLayer.fromFunction(SpendingRepositoryImpl.apply _)
}

final case class SpendingRepositoryImpl(quill: Quill.Postgres[SnakeCase])
    extends SpendingRepository {
  import quill._

  implicit val incomeInsertMeta: quill.InsertMeta[SpendingRecord] = insertMeta[SpendingRecord](_.id)

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
    ): ZIO[Any, SQLException, List[SpendingRecord]] =
    run(
      qs.filter(_.usrId == lift(UUID.fromString(uuid)))
    )
}
