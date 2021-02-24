package dott.cli

import cats.effect._
import cats.implicits._
import com.monovore.decline._
import com.monovore.decline.effect._
import dott.cli.domain.Interval
import dott.cli.persistence.OrderStore
import dott.cli.rules.ProductRules

object Cli extends CommandIOApp(name = "dott-cli", header = "Cli to check if a product is still available to buy") {

  override def main: Opts[IO[ExitCode]] = {
    Commands.filterOrdersOpts
      .map({ command =>
        val orderStore = OrderStore.impl[IO]()
        val productRules = ProductRules.impl[IO](orderStore)
        productRules.get(command.startDate, command.endDate, command.intervals.toList).map { results =>
          results.traverse {
            case (interval, count) =>
              generateOutput(interval, count)
          }
        }
      })
      .map(_.map(_.fold(error => error, output => {
        println(output.mkString("\n"))
        ExitCode.Success
      })))
  }

  private def generateOutput(interval: Interval, count: Int): Either[ExitCode, String] = {
    interval match {
      case Interval(Some(minMonth), Some(maxMonth)) =>
        s"$minMonth-$maxMonth months: $count orders".asRight
      case Interval(Some(minMonth), None) =>
        s"> $minMonth months: $count orders".asRight
      case Interval(None, Some(maxMonth)) =>
        s"< $maxMonth months: $count orders".asRight
      case _ => ExitCode.Error.asLeft
    }
  }
}
