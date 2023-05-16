package com.alexrezv.personalfinances.services.impl

import com.alexrezv.personalfinances.dao.repositories.{
  IncomeRepository,
  SpendingRepository,
  UserRepository
}
import com.alexrezv.personalfinances.dto.{IncomeRecordDTO, SpendingRecordDTO, UserDTO}
import com.alexrezv.personalfinances.services.UserService
import zio._

import java.sql.SQLException
import javax.sql.DataSource

object UserServiceImpl {
  val layer: ZLayer[UserRepository & IncomeRepository & SpendingRepository, Nothing, UserService] =
    ZLayer {
      for {
        userRepository     <- ZIO.service[UserRepository]
        incomeRepository   <- ZIO.service[IncomeRepository]
        spendingRepository <- ZIO.service[SpendingRepository]
      } yield UserServiceImpl(userRepository, incomeRepository, spendingRepository)
    }
}

case class UserServiceImpl(
    userRepository: UserRepository,
    incomeRepository: IncomeRepository,
    spendingRepository: SpendingRepository
  ) extends UserService {
  override def findAll(): ZIO[DataSource, SQLException, List[UserDTO]] = for {
    users <- userRepository.getUsers
  } yield users.map(UserDTO.from)

  override def findByUUID(uuid: String): ZIO[DataSource, Option[SQLException], UserDTO] = for {
    user <- userRepository.getUserByUUID(uuid).some
  } yield UserDTO.from(user)

  override def findIncomes(uuid: String): ZIO[DataSource, Serializable, List[IncomeRecordDTO]] =
    for {
      user    <- userRepository.getUserByUUID(uuid).some
      incomes <- incomeRepository.getIncomeRecordsByUserId(user.uuid.toString)
    } yield incomes.map(it => IncomeRecordDTO.from(it))

  override def findSpendings(uuid: String): ZIO[DataSource, Serializable, List[SpendingRecordDTO]] =
    for {
      user      <- userRepository.getUserByUUID(uuid).some
      spendings <- spendingRepository.getSpendingRecordsByUserId(user.uuid.toString)
    } yield spendings.map(SpendingRecordDTO.from)
}
