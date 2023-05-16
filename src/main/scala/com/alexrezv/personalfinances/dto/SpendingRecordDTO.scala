package com.alexrezv.personalfinances.dto

import com.alexrezv.personalfinances.dao.entities.SpendingRecord
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

case class SpendingRecordDTO(
    id: Long,
    date: String,
    amount: Double,
    category: String,
    comment: Option[String]
  )

object SpendingRecordDTO {
  implicit val decoder: Decoder[SpendingRecordDTO] = deriveDecoder
  implicit val encoder: Encoder[SpendingRecordDTO] = deriveEncoder

  def from(incomeRecord: SpendingRecord): SpendingRecordDTO = SpendingRecordDTO(
    incomeRecord.id,
    dateFormat.format(incomeRecord.date),
    incomeRecord.amount,
    incomeRecord.category.name,
    incomeRecord.comment
  )
}
