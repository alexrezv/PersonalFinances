package com.alexrezv.personalfinances.dao.repositories

import com.alexrezv.personalfinances.dao.entities.SpendingRecord
import io.getquill.context.ZioJdbc.QIO
import zio.{&, ZIO}

import java.sql.SQLException
import javax.sql.DataSource

trait SpendingRepository {
  def getSpendingRecordsByUserId(uuid: String): QIO[List[SpendingRecord]]
}

object SpendingRepository {
  def getSpendingRecordsByUserId(
      uuid: String
    ): ZIO[DataSource & SpendingRepository, SQLException, List[SpendingRecord]] =
    ZIO.serviceWithZIO[SpendingRepository](_.getSpendingRecordsByUserId(uuid))
}
