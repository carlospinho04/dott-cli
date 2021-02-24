package dott.cli.rules

import cats.effect.IO
import dott.cli.domain.{Interval, Product, ProductIdentifier}
import dott.cli.persistence.OrderStore
import munit.CatsEffectSuite

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class ProductRulesSpec extends CatsEffectSuite {

  val validOrderStore: OrderStore[IO] = (_: LocalDateTime, _: LocalDateTime) =>
    IO(
      Seq(
        Product(ProductIdentifier(1),
                "cookies",
                "food",
                0.3f,
                BigDecimal(1.3),
                LocalDateTime.now().minus(1, ChronoUnit.MONTHS)),
        Product(ProductIdentifier(1),
                "rice",
                "food",
                0.3f,
                BigDecimal(1.3),
                LocalDateTime.now().minus(3, ChronoUnit.MONTHS)),
        Product(ProductIdentifier(1),
                "computer",
                "technology",
                3.0f,
                BigDecimal(900),
                LocalDateTime.now().minus(7, ChronoUnit.MONTHS))
      )
  )

  val emptyOrderStore: OrderStore[IO] = (_: LocalDateTime, _: LocalDateTime) => IO(Seq.empty[Product])

  test("Should group correctly orders") {
    val firstInterval = Interval(Some(1), Some(3))
    val secondInterval = Interval(Some(4), None)

    val projectRules = ProductRules.impl(validOrderStore)

    val result = projectRules.get(LocalDateTime.now().minus(1, ChronoUnit.YEARS),
                                  LocalDateTime.now().plus(1, ChronoUnit.YEARS),
                                  Seq(firstInterval, secondInterval))

    assertIO(result, Seq((firstInterval, 2), (secondInterval, 1)))
  }

  test("Should work correctly when order store returns empty sequence") {
    val firstInterval = Interval(Some(1), Some(3))
    val secondInterval = Interval(Some(4), None)

    val projectRules = ProductRules.impl(emptyOrderStore)

    val result = projectRules.get(LocalDateTime.now().minus(1, ChronoUnit.YEARS),
                                  LocalDateTime.now().plus(1, ChronoUnit.YEARS),
                                  Seq(firstInterval, secondInterval))

    assertIO(result, Seq((firstInterval, 0), (secondInterval, 0)))
  }
}
