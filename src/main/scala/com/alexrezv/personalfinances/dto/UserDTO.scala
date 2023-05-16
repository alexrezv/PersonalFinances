package com.alexrezv.personalfinances.dto

import com.alexrezv.personalfinances.dao.entities.User
import io.circe._
import io.circe.generic.semiauto._

case class UserDTO(uuid: String, userName: String)

object UserDTO {
  implicit val decoder: Decoder[UserDTO] = deriveDecoder
  implicit val encoder: Encoder[UserDTO] = deriveEncoder

  def from(user: User): UserDTO = UserDTO(
    user.uuid.toString,
    user.userName
  )
}
