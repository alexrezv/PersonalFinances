package com.alexrezv.personalfinances.services.impl

import com.alexrezv.personalfinances.dao.entities.User
import com.alexrezv.personalfinances.dao.repositories.{IncomeRepository, SpendingRepository, UserRepository}
import com.alexrezv.personalfinances.dto.{IncomeRecordDTO, SpendingRecordDTO, UserDTO}
import com.alexrezv.personalfinances.services.{CryptoService, UserService}
import zio._
import zio.crypto.encryption.CipherText

import java.sql.SQLException
import javax.sql.DataSource

object UserServiceImpl {
  val layer
      : ZLayer[UserRepository & IncomeRepository & SpendingRepository & CryptoService, Nothing, UserService] =
    ZLayer {
      for {
        userRepository     <- ZIO.service[UserRepository]
        incomeRepository   <- ZIO.service[IncomeRepository]
        spendingRepository <- ZIO.service[SpendingRepository]
        cryptoService      <- ZIO.service[CryptoService]
      } yield UserServiceImpl(userRepository, incomeRepository, spendingRepository, cryptoService)
    }
}

case class UserServiceImpl(
    userRepository: UserRepository,
    incomeRepository: IncomeRepository,
    spendingRepository: SpendingRepository,
    cryptoService: CryptoService
  ) extends UserService {
  override def create(user: UserDTO): ZIO[DataSource, Serializable, String] =
    for {
      uuid              <- zio.Random.nextUUID
      password          <- ZIO.fromOption(user.password)
      encryptedPassword <- cryptoService.encrypt(password)
      _ <- userRepository.insert(User(0L, uuid, user.userName, encryptedPassword.value))
    } yield uuid.toString

  override def findAll(): ZIO[DataSource, SQLException, List[UserDTO]] = for {
    users <- userRepository.list
  } yield users.map(UserDTO.from)

  override def findByUUID(uuid: String): ZIO[DataSource, Option[SQLException], UserDTO] = for {
    user <- userRepository.findByUUID(uuid).some
  } yield UserDTO.from(user)

  def findByLoginWithPassword(userName: String): ZIO[DataSource, Serializable, UserDTO] = for {
    user <- userRepository.findByLogin(userName).some
    decp <- cryptoService.decrypt(CipherText(user.password))
  } yield UserDTO(Some(user.uuid.toString), user.userName, Some(decp))

  override def findIncomes(uuid: String): ZIO[DataSource, Serializable, List[IncomeRecordDTO]] =
    for {
      user    <- userRepository.findByUUID(uuid).some
      incomes <- incomeRepository.getIncomeRecordsByUserId(user.uuid.toString)
    } yield incomes.map(it => IncomeRecordDTO.from(it))

  override def findSpendings(uuid: String): ZIO[DataSource, Serializable, List[SpendingRecordDTO]] =
    for {
      user      <- userRepository.findByUUID(uuid).some
      spendings <- spendingRepository.getSpendingRecordsByUserId(user.uuid.toString)
    } yield spendings.map(SpendingRecordDTO.from)
}
