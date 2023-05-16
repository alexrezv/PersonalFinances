package com.alexrezv.personalfinances.api

import com.alexrezv.personalfinances.services.UserService
import io.circe.syntax._
import zio.http._
import zio.{&, ZIO}

import javax.sql.DataSource

object PersonalFinancesAPI {
  val api: Http[UserService & DataSource, Nothing, Request, Response] = Http.collectZIO[Request] {
    //list users
    case Method.GET -> !! / "users" =>
      UserService
        .findAll()
        .foldZIO(
          _ => ZIO.succeed(Response.status(Status.NotFound)),
          res => ZIO.succeed(Response.json(res.asJson.toString()))
        )

    // get user by it's uuid
    case Method.GET -> !! / "user" / uuid =>
      UserService
        .findByUUID(uuid)
        .foldZIO(
          _ => ZIO.succeed(Response.status(Status.NotFound)),
          res => ZIO.succeed(Response.json(res.asJson.toString()))
        )

    // list user's incomes
    case Method.GET -> !! / "user" / uuid / "incomes" =>
      UserService
        .findIncomes(uuid)
        .foldZIO(
          _ => ZIO.succeed(Response.status(Status.NotFound)),
          res => ZIO.succeed(Response.json(res.asJson.toString()))
        )

    // list user's spendings
    case Method.GET -> !! / "user" / uuid / "spendings" =>
      UserService
        .findSpendings(uuid)
        .foldZIO(
          _ => ZIO.succeed(Response.status(Status.NotFound)),
          res => ZIO.succeed(Response.json(res.asJson.toString()))
        )

  }
}
