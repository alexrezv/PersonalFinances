package com.alexrezv.personalfinances.dao.entities

sealed trait SpendingCategory {
  val name: String

  override def toString: String = name
}

object SpendingCategory {
  private object Bills extends SpendingCategory {
    override val name: String = "Bills"
  }

  private object Rent extends SpendingCategory {
    override val name: String = "Rent"
  }

  private object Food extends SpendingCategory {
    override val name: String = "Food"
  }

  private object Clothes extends SpendingCategory {
    override val name: String = "Clothes"
  }

  private object Electronics extends SpendingCategory {
    override val name: String = "Electronics"
  }

  private object Transport extends SpendingCategory {
    override val name: String = "Transport"
  }

  private object Insurance extends SpendingCategory {
    override val name: String = "Insurance"
  }

  private object Taxes extends SpendingCategory {
    override val name: String = "Taxes"
  }

  private object Other extends SpendingCategory {
    override val name: String = "Other"
  }

  private lazy val categories = List(
    Bills,
    Rent,
    Food,
    Clothes,
    Electronics,
    Transport,
    Insurance,
    Taxes,
    Other
  ).map(c => (c.name.toLowerCase, c)).toMap

  def fromString(value: String): Option[SpendingCategory] = categories.get(value.toLowerCase)
}
