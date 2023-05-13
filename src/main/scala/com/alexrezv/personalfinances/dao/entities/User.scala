package com.alexrezv.personalfinances.dao.entities

import java.util.UUID

case class User(
    id: Long,
    uuid: UUID,
    userName: String
  )
