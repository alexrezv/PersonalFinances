package com.alexrezv.personalfinances

import zio._
import zio.http.Method.GET
import zio.http._
import zio.test.Assertion._
import zio.test._

object PersonalFinancesSpec extends ZIOSpecDefault {

  def makeTests: ZIO[Any, Nothing, List[Spec[Any, Option[Nothing]]]] =
    ZIO.succeed(
      List("text", "json").map(uri => makeTest(Request.default(GET, URL(!! / uri)))),
    )

  def makeTest(request: Request): Spec[Any, Option[Nothing]] =
    test(s"test path ${request.path} is 200 ok") {
      assertZIO(PersonalFinances.app.runZIO(request).map(_.status))(equalTo(Status.Ok))
    }

  override def spec: Spec[TestEnvironment with Scope, Any] =
    suite("""PersonalFinancesSpec""")(makeTests)

}
