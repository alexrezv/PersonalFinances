package com.alexrezv.personalfinances.services

import com.alexrezv.personalfinances.services.impl.CryptoServiceImpl
import zio.crypto.encryption.SymmetricEncryption
import zio.test._
import zio.{&, Scope}

object CryptoServiceSpec extends ZIOSpecDefault {
  override def spec: Spec[TestEnvironment & Scope, Any] = suite("""CryptoServiceSpec""")(
    test("encryption test") {
      for {
        encrypted <- CryptoService.encrypt("foo")
        decrypted <- CryptoService.decrypt(encrypted)
      } yield assertTrue(decrypted == "foo")
    }
  ).provide(CryptoServiceImpl.layer, SymmetricEncryption.live)
}
