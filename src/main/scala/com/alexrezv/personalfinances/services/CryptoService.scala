package com.alexrezv.personalfinances.services

import zio.crypto.encryption.CipherText
import zio.{Task, ZIO}

trait CryptoService {
  def encrypt(string: String): Task[CipherText[String]]

  def decrypt(encryptedString: CipherText[String]): Task[String]
}

object CryptoService {
  def encrypt(string: String): ZIO[CryptoService, Throwable, CipherText[String]] =
    ZIO.serviceWithZIO[CryptoService](_.encrypt(string))

  def decrypt(encryptedString: CipherText[String]): ZIO[CryptoService, Throwable, String] =
    ZIO.serviceWithZIO[CryptoService](_.decrypt(encryptedString))
}
