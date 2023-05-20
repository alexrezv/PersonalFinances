package com.alexrezv.personalfinances.services

import com.alexrezv.personalfinances.dto.{IncomeRecordDTO, SpendingRecordDTO, UserDTO}
import zio.{&, ZIO}

import java.sql.SQLException
import javax.sql.DataSource

trait UserService {
  def create(user: UserDTO): ZIO[DataSource, Serializable, String]

  def findAll(): ZIO[DataSource, SQLException, List[UserDTO]]

  def findByUUID(uuid: String): ZIO[DataSource, Option[SQLException], UserDTO]
  def findByLoginWithPassword(userName: String): ZIO[DataSource, Serializable, UserDTO]

  def findIncomes(uuid: String): ZIO[DataSource, Serializable, List[IncomeRecordDTO]]

  def findSpendings(uuid: String): ZIO[DataSource, Serializable, List[SpendingRecordDTO]]
}

object UserService {
  def create(user: UserDTO): ZIO[DataSource & UserService, Serializable, String] =
    ZIO.serviceWithZIO[UserService](_.create(user))
  def findAll(): ZIO[DataSource & UserService, SQLException, List[UserDTO]]      =
    ZIO.serviceWithZIO[UserService](_.findAll())

  def findByUUID(uuid: String): ZIO[DataSource & UserService, Option[SQLException], UserDTO] =
    ZIO.serviceWithZIO[UserService](_.findByUUID(uuid))

  def findByLoginWithPassword(userName: String): ZIO[DataSource & UserService, Serializable, UserDTO] =
    ZIO.serviceWithZIO[UserService](_.findByLoginWithPassword(userName))

  def findIncomes(
      uuid: String
    ): ZIO[DataSource & UserService, Serializable, List[IncomeRecordDTO]] =
    ZIO.serviceWithZIO[UserService](_.findIncomes(uuid))

  def findSpendings(
      uuid: String
    ): ZIO[DataSource & UserService, Serializable, List[SpendingRecordDTO]] =
    ZIO.serviceWithZIO[UserService](_.findSpendings(uuid))
}
