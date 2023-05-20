package com.alexrezv.personalfinances.api

import com.alexrezv.personalfinances.dto.UserDTO
import com.alexrezv.personalfinances.services.UserService
import zio._
import zio.http._
import zio.json._

import javax.sql.DataSource

object PersonalFinancesAPI {
  private val authMiddleWare
      : RequestHandlerMiddleware[Nothing, DataSource & UserService, Serializable, Boolean] =
    RequestHandlerMiddlewares.customAuthZIO(
      verify = headers =>
        headers.get(Header.Authorization) match {
          case Some(Header.Authorization.Basic("finan", "finan"))   => ZIO.succeed(true)
          case Some(Header.Authorization.Basic(userName, password)) =>
            for {
              user <- UserService.findByLoginWithPassword(userName)
              pass <- ZIO.fromOption(user.password)
            } yield pass.equals(password)
          case _                                                    => ZIO.succeed(false)
        }
    )

  // compose basic auth, request/response logging, timeouts middlewares
  val composedMiddlewares = /*RequestHandlerMiddlewares.basicAuth("finan", "finan") ++*/
    authMiddleWare ++
      RequestHandlerMiddlewares.debug ++
      RequestHandlerMiddlewares.timeout(5.seconds)

  val api = Http.collectZIO[Request] {
    case request @ Method.POST -> !! / "user" =>
      (for {
        body   <- request.body.asString(Charsets.Utf8)
        dto    <- ZIO.fromEither(UserDTO.decoder.decodeJson(body))
        result <- UserService.create(dto)
      } yield result).foldZIO(
        _ => ZIO.succeed(Response.status(Status.BadRequest)),
        res => ZIO.succeed(Response.json(res))
      )

    //list users
    case Method.GET -> !! / "users" =>
      UserService
        .findAll()
        .foldZIO(
          _ => ZIO.succeed(Response.status(Status.NotFound)),
          res => ZIO.succeed(Response.json(res.toJson))
        )

    // get user by it's uuid
    case Method.GET -> !! / "user" / uuid =>
      UserService
        .findByUUID(uuid)
        .foldZIO(
          _ => ZIO.succeed(Response.status(Status.NotFound)),
          res => ZIO.succeed(Response.json(res.toJson))
        )

    // list user's incomes
    case Method.GET -> !! / "user" / uuid / "incomes" =>
      UserService
        .findIncomes(uuid)
        .foldZIO(
          _ => ZIO.succeed(Response.status(Status.NotFound)),
          res => ZIO.succeed(Response.json(res.toJson))
        )

    // list user's spendings
    case Method.GET -> !! / "user" / uuid / "spendings" =>
      UserService
        .findSpendings(uuid)
        .foldZIO(
          _ => ZIO.succeed(Response.status(Status.NotFound)),
          res => ZIO.succeed(Response.json(res.toJson))
        )

  } @@ composedMiddlewares
}
