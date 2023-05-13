package com.alexrezv.personalfinances.dao.repositories

import com.alexrezv.personalfinances.dao.entities.SpendingRecord
import zio.ZIO

import java.sql.SQLException

trait SpendingRepository {
  def getSpendingRecordsByUserId(uuid: String): ZIO[Any, SQLException, List[SpendingRecord]]
}

object SpendingRepository {
  def getSpendingRecordsByUserId(
      uuid: String
    ): ZIO[SpendingRepository, SQLException, List[SpendingRecord]] =
    ZIO.serviceWithZIO[SpendingRepository](_.getSpendingRecordsByUserId(uuid))
}
