package dott.cli.rules

import cats.effect.Async
import cats.implicits._
import dott.cli.domain.Interval
import dott.cli.persistence.OrderStore

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

trait ProductRules[F[_]] {
  def get(startDate: LocalDateTime, endDate: LocalDateTime, intervals: Seq[Interval]): F[Seq[(Interval, Int)]]
}

object ProductRules {

  def impl[F[_]: Async](orderStore: OrderStore[F]): ProductRules[F] = new ProductRules[F] {
    override def get(startDate: LocalDateTime,
                     endDate: LocalDateTime,
                     intervals: Seq[Interval]): F[Seq[(Interval, Int)]] = {
      orderStore.getProductsInInterval(startDate, endDate).map { products =>
        intervals.map { interval =>
          val productsFiltered = products.filter { product =>
            val diffInMonths = ChronoUnit.MONTHS.between(product.creationDate, LocalDateTime.now())
            interval.minMonth.forall(minMonth => diffInMonths >= minMonth) && interval.maxMonth
              .forall(maxMonth => diffInMonths <= maxMonth)
          }
          (interval, productsFiltered.size)
        }
      }
    }
  }

}
