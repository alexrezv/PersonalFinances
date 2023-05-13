package com.alexrezv.personalfinances.dao.repositories

import com.alexrezv.personalfinances.dao.entities.IncomeRecord
import zio.ZIO

import java.sql.SQLException

trait IncomeRepository {
  def getIncomeRecordsByUserId(uuid: String): ZIO[Any, SQLException, List[IncomeRecord]]
}

object IncomeRepository {
  def getIncomeRecordsByUserId(
      uuid: String
    ): ZIO[IncomeRepository, SQLException, List[IncomeRecord]] =
    ZIO.serviceWithZIO[IncomeRepository](_.getIncomeRecordsByUserId(uuid))
}
