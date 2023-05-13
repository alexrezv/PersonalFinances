package com.alexrezv.personalfinances.dao.entities

sealed trait IncomeCategory {
  val name: String

  override def toString: String = name
}

object IncomeCategory {
  private object Salary extends IncomeCategory {
    override val name: String = "Salary"
  }

  private object Bonus extends IncomeCategory {
    override val name: String = "Bonus"
  }

  private object Trading extends IncomeCategory {
    override val name: String = "Trading"
  }

  private object Dividends extends IncomeCategory {
    override val name: String = "Dividends"
  }

  private object Other extends IncomeCategory {
    override val name: String = "Other"
  }

  private lazy val categories = List(
    Salary,
    Bonus,
    Trading,
    Dividends,
    Other
  ).map(c => (c.name.toLowerCase, c)).toMap

  def fromString(value: String): Option[IncomeCategory] = categories.get(value.toLowerCase)
}
