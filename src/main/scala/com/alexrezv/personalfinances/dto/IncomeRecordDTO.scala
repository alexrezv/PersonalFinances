package com.alexrezv.personalfinances.dto

import com.alexrezv.personalfinances.dao.entities.IncomeRecord
import zio.json._

//@jsonDerive
case class IncomeRecordDTO(
    id: Long,
    date: String,
    amount: Double,
    category: String,
    comment: Option[String]
  )

object IncomeRecordDTO {
  implicit val decoder: JsonDecoder[IncomeRecordDTO] = DeriveJsonDecoder.gen[IncomeRecordDTO]
  implicit val encoder: JsonEncoder[IncomeRecordDTO] = DeriveJsonEncoder.gen[IncomeRecordDTO]

  def from(incomeRecord: IncomeRecord): IncomeRecordDTO = IncomeRecordDTO(
    incomeRecord.id,
    dateFormat.format(incomeRecord.date),
    incomeRecord.amount,
    incomeRecord.category.name,
    incomeRecord.comment
  )
}
