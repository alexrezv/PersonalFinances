package com.alexrezv.personalfinances.services.impl

import com.alexrezv.personalfinances.services.CryptoService
import com.google.crypto.tink.{KeyTemplates, KeysetHandle}
import zio.crypto.encryption.{CipherText, SymmetricEncryption, SymmetricEncryptionAlgorithm}
import zio.crypto.keyset.Keyset
import zio.{Task, ZIO, ZLayer}

import java.nio.charset.Charset

object CryptoServiceImpl {
  val layer: ZLayer[SymmetricEncryption, Nothing, CryptoServiceImpl] =
    ZLayer {
      for {
        encryptionService <- ZIO.service[SymmetricEncryption]
      } yield CryptoServiceImpl(encryptionService)
    }
}

case class CryptoServiceImpl(encryptionService: SymmetricEncryption) extends CryptoService {
  private val key: Keyset[SymmetricEncryptionAlgorithm] =
    new Keyset[SymmetricEncryptionAlgorithm](
      KeysetHandle.generateNew(KeyTemplates.get("AES256_GCM"))
    )

  override def encrypt(string: String): Task[CipherText[String]] =
    encryptionService.encrypt(string, key, Charset.defaultCharset())

  override def decrypt(encryptedString: CipherText[String]): Task[String] =
    encryptionService.decrypt(encryptedString, key, Charset.defaultCharset())
}
