package com.alexrezv.personalfinances.dao.entities

import java.util.{Date, UUID}

case class IncomeRecord(
    id: Long,
    usrId: UUID,
    date: Date,
    amount: Double,
    category: IncomeCategory,
    comment: Option[String]
  )
