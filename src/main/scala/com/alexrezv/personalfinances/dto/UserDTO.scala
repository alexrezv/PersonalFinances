package com.alexrezv.personalfinances.dto

import com.alexrezv.personalfinances.dao.entities.User
import zio.json._

//@jsonDerive - won't work for some reason
case class UserDTO(
    uuid: Option[String],
    userName: String,
    password: Option[String]
  )

object UserDTO {
  implicit val decoder: JsonDecoder[UserDTO] = DeriveJsonDecoder.gen[UserDTO]
  implicit val encoder: JsonEncoder[UserDTO] = DeriveJsonEncoder.gen[UserDTO]

  def from(user: User): UserDTO = UserDTO(
    Some(user.uuid.toString),
    user.userName,
    None
  )
}
