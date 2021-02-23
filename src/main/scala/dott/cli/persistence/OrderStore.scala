package dott.cli.persistence

import cats.effect.{Effect, IO}
import dott.cli.DateTime.formatter
import dott.cli.domain.{Product, ProductIdentifier}

import java.time.LocalDateTime

trait OrderStore[F[_]] {
  def getProductsInInterval(startDate: LocalDateTime, endDate: LocalDateTime): F[Seq[Product]]
}

object OrderStore {

  def impl[F[_] : Effect](): OrderStore[F] = new OrderStore[F] {
    override def getProductsInInterval(startDate: LocalDateTime, endDate: LocalDateTime): F[Seq[Product]] = {
      val date1 = "2018-03-04T11:30:40"
      val date2 = "2018-07-04T11:30:40"
      Effect[F].liftIO(IO(Seq(Product(ProductIdentifier(1), "cookies", "food", 0.3f, BigDecimal(1.3), LocalDateTime.parse(date2, formatter)),
        Product(ProductIdentifier(1), "rice", "food", 0.3f, BigDecimal(1.3), LocalDateTime.parse(date2, formatter)))))
    }
  }

}