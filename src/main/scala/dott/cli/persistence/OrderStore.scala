package dott.cli.persistence

import cats.effect.{Effect, IO}
import dott.cli.domain.{Product, ProductIdentifier}
import dott.cli.formatter.DateTime.formatter

import java.time.LocalDateTime

trait OrderStore[F[_]] {
  def getProductsInInterval(startDate: LocalDateTime, endDate: LocalDateTime): F[Seq[Product]]
}

object OrderStore {

  /*
  Future improvements: Having h2(https://www.h2database.com/html/main.html) and doobie(https://tpolecat.github.io/doobie/) here can be a solution
  A way to get this output we could do:
  Having a relational db, we could filter all orders in the desired period, then we could get all the items for those orders and then get
  the Product to get the creation date.
   */
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
                    "computer",
                    "technology",
                    3.0f,
                    BigDecimal(900),
                    LocalDateTime.parse("2020-04-04T11:30:40", formatter))
          )
        )
      )
    }
  }

}
