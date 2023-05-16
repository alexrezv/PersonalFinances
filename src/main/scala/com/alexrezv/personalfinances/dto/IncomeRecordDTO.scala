package com.alexrezv.personalfinances.dto

import com.alexrezv.personalfinances.dao.entities.IncomeRecord
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

case class IncomeRecordDTO(
    id: Long,
    date: String,
    amount: Double,
    category: String,
    comment: Option[String]
  )

object IncomeRecordDTO {
  implicit val decoder: Decoder[IncomeRecordDTO] = deriveDecoder
  implicit val encoder: Encoder[IncomeRecordDTO] = deriveEncoder

  def from(incomeRecord: IncomeRecord): IncomeRecordDTO = IncomeRecordDTO(
    incomeRecord.id,
    dateFormat.format(incomeRecord.date),
    incomeRecord.amount,
    incomeRecord.category.name,
    incomeRecord.comment
  )
}
