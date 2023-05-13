package com.alexrezv.personalfinances.dao.entities

import java.util.{Date, UUID}

case class SpendingRecord(
    id: Long,
    usrId: UUID,
    date: Date,
    amount: Double,
    category: SpendingCategory,
    comment: Option[String]
  )
