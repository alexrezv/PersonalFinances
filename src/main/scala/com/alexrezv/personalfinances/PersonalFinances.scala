package com.alexrezv.personalfinances

import com.alexrezv.personalfinances.api.PersonalFinancesAPI
import com.alexrezv.personalfinances.dao.repositories.impl.{IncomeRepositoryImpl, SpendingRepositoryImpl, UserRepositoryImpl}
import com.alexrezv.personalfinances.db.dsLayer
import com.alexrezv.personalfinances.services.impl.UserServiceImpl
import zio._
import zio.http.Server

object PersonalFinances extends ZIOAppDefault {
  private val myEnv =
    dsLayer >+>
      UserRepositoryImpl.layer ++
      IncomeRepositoryImpl.layer ++
      SpendingRepositoryImpl.layer >+>
      UserServiceImpl.layer

  private val httpApp = PersonalFinancesAPI.api

  // Run it like any simple app
  override val run: ZIO[Any & ZIOAppArgs & Scope, Any, Any] =
    Server
      .serve(httpApp)
      .provide(Server.default, myEnv)
}
