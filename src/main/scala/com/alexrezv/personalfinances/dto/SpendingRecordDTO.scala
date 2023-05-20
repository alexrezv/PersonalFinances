package com.alexrezv.personalfinances.dto

import com.alexrezv.personalfinances.dao.entities.SpendingRecord
import zio.json._

//@jsonDerive
case class SpendingRecordDTO(
    id: Long,
    date: String,
    amount: Double,
    category: String,
    comment: Option[String]
  )

object SpendingRecordDTO {
  implicit val decoder: JsonDecoder[SpendingRecordDTO] = DeriveJsonDecoder.gen[SpendingRecordDTO]
  implicit val encoder: JsonEncoder[SpendingRecordDTO] = DeriveJsonEncoder.gen[SpendingRecordDTO]

  def from(incomeRecord: SpendingRecord): SpendingRecordDTO = SpendingRecordDTO(
    incomeRecord.id,
    dateFormat.format(incomeRecord.date),
    incomeRecord.amount,
    incomeRecord.category.name,
    incomeRecord.comment
  )
}
