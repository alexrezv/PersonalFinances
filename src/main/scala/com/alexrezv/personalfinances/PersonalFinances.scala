package com.alexrezv.personalfinances

import com.alexrezv.personalfinances.api.PersonalFinancesAPI
import com.alexrezv.personalfinances.dao.repositories.impl.{IncomeRepositoryImpl, SpendingRepositoryImpl, UserRepositoryImpl}
import com.alexrezv.personalfinances.db.dsLayer
import com.alexrezv.personalfinances.services.impl.{CryptoServiceImpl, UserServiceImpl}
import zio._
import zio.crypto.encryption.SymmetricEncryption
import zio.http.Server

object PersonalFinances extends ZIOAppDefault {
  private val httpApp = PersonalFinancesAPI.api

  // Run it like any simple app
  override val run: ZIO[Any & ZIOAppArgs & Scope, Any, Any] =
    Server
      .serve(httpApp)
      .provide(
        Server.default,
        dsLayer,
        UserRepositoryImpl.layer,
        IncomeRepositoryImpl.layer,
        SpendingRepositoryImpl.layer,
        SymmetricEncryption.live,
        CryptoServiceImpl.layer,
        UserServiceImpl.layer
      )
}
