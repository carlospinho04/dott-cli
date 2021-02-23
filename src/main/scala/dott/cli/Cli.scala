package dott.cli

import cats.effect._
import com.monovore.decline._
import com.monovore.decline.effect._
import dott.cli.persistence.OrderStore
import dott.cli.rules.ProductRules

object Cli
  extends CommandIOApp(
    name = "dott-cli",
    header = "Cli to check if a product is still available to buy"
  ) {

  override def main: Opts[IO[ExitCode]] = {

    Commands.filterOrdersOpts.map({
      command =>
        val orderStore = OrderStore.impl[IO]()
        val productRules = ProductRules.impl[IO](orderStore)
        productRules.get(command.startDate, command.endDate).map(println(_))
    })
      .map(_.as(ExitCode.Success))
  }
}