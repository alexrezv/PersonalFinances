package com.alexrezv.personalfinances

import com.alexrezv.personalfinances.dao.repositories.impl.{IncomeRepositoryImpl, SpendingRepositoryImpl, UserRepositoryImpl}
import com.alexrezv.personalfinances.dao.repositories.{IncomeRepository, SpendingRepository, UserRepository}
import com.alexrezv.personalfinances.db.{dsLayer, quillLayer}
import zio.http._
import zio.{Console, _}

object PersonalFinances extends ZIOAppDefault {

  // Create HTTP route
  val app: HttpApp[Any, Nothing] = Http.collect[Request] {
    case Method.GET -> !! / "text" => Response.text("Hello World!")
    case Method.GET -> !! / "json" => Response.json("""{"greetings": "Hello World!"}""")
  }

  // Run it like any simple app
  override val run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] =
    //    Server.serve(app).provide(Server.default)

    UserRepository
      .getUserByUUID("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11")
      .tapSome {
        case Some(value) => Console.printLine(value)
        case None        => Console.printLine("Nothing!")
      }
      .provide(quillLayer, dsLayer, UserRepositoryImpl.layer) *>
      IncomeRepository
        .getIncomeRecordsByUserId("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11")
        .tap(it => ZIO.foreach(it)(Console.printLine(_)))
        .provide(quillLayer, dsLayer, IncomeRepositoryImpl.layer) *>
      SpendingRepository
        .getSpendingRecordsByUserId("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11")
        .tap(it => ZIO.foreach(it)(Console.printLine(_)))
        .provide(quillLayer, dsLayer, SpendingRepositoryImpl.layer)
        .exitCode
}
