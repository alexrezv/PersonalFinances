package com.alexrezv.personalfinances

import zio._
import zio.http._

object PersonalFinances extends ZIOAppDefault {

  // Create HTTP route
  val app: HttpApp[Any, Nothing] = Http.collect[Request] {
    case Method.GET -> !! / "text" => Response.text("Hello World!")
    case Method.GET -> !! / "json" => Response.json("""{"greetings": "Hello World!"}""")
  }

  // Run it like any simple app
  override val run: ZIO[Any, Throwable, Nothing] =
    Server.serve(app).provide(Server.default)

}
