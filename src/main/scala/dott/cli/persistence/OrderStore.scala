package dott.cli.persistence

import cats.effect.{Effect, IO}
import dott.cli.domain.{Product, ProductIdentifier}
import dott.cli.formatter.DateTime.formatter

import java.time.LocalDateTime

trait OrderStore[F[_]] {
  def getProductsInInterval(startDate: LocalDateTime, endDate: LocalDateTime): F[Seq[Product]]
}

object OrderStore {

  //Future improvements: Having h2 and doobie here could be a thing
  def impl[F[_]: Effect](): OrderStore[F] = new OrderStore[F] {
    override def getProductsInInterval(startDate: LocalDateTime, endDate: LocalDateTime): F[Seq[Product]] = {
      Effect[F].liftIO(
        IO(
          Seq(
            Product(ProductIdentifier(1),
                    "cookies",
                    "food",
                    0.3f,
                    BigDecimal(1.3),
                    LocalDateTime.parse("2020-03-04T11:30:40", formatter)),
            Product(ProductIdentifier(1),
                    "rice",
                    "food",
                    0.3f,
                    BigDecimal(1.3),
                    LocalDateTime.parse("2020-07-04T11:30:40", formatter)),
            Product(ProductIdentifier(1),
                    "ric fazeres",
                    "food",
                    0.3f,
                    BigDecimal(1.3),
                    LocalDateTime.parse("2020-04-04T11:30:40", formatter))
          )
        )
      )
    }
  }

}
