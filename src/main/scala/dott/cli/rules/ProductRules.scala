package dott.cli.rules

import cats.effect.Effect
import dott.cli.persistence.OrderStore
import dott.cli.domain.{Interval, Product}

import java.time.LocalDateTime

trait ProductRules[F[_]] {
  def get(startDate: LocalDateTime, endDate: LocalDateTime): F[Seq[Product]]
}

object ProductRules {

  def impl[F[_]: Effect](orderStore: OrderStore[F]): ProductRules[F] = new ProductRules[F] {
    override def get(startDate: LocalDateTime, endDate: LocalDateTime): F[Seq[Product]] = {
      val intervals: Seq[Interval] = Seq(Interval(1, 3), Interval(4, 7), Interval(8, 12))
      val products = orderStore.getProductsInInterval(startDate, endDate)
    }
  }

}