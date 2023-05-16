package com.alexrezv.personalfinances.dao.repositories

import com.alexrezv.personalfinances.dao.entities.IncomeRecord
import io.getquill.context.ZioJdbc.QIO
import zio.{&, ZIO}

import java.sql.SQLException
import javax.sql.DataSource

trait IncomeRepository {
  def getIncomeRecordsByUserId(uuid: String): QIO[List[IncomeRecord]]
}

object IncomeRepository {
  def getIncomeRecordsByUserId(
      uuid: String
    ): ZIO[DataSource & IncomeRepository, SQLException, List[IncomeRecord]] =
    ZIO.serviceWithZIO[IncomeRepository](_.getIncomeRecordsByUserId(uuid))
}
