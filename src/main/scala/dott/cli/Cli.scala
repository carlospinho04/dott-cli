package example

import cats.effect._
import com.monovore.decline._
import com.monovore.decline.effect._

import java.time.ZoneOffset

object Cli
  extends CommandIOApp(
    name = "dott-cli",
    header = "Cli to check if a product is still available to buy"
  ) {

  override def main: Opts[IO[ExitCode]] = {

    Commands.filterOrdersOpts.map({
      command =>
        IO {
          println(s"start date: ${command.startDate.toInstant(ZoneOffset.UTC).toEpochMilli}, end date: ${command.endDate.toInstant(ZoneOffset.UTC).toEpochMilli}")
        }
    })
      .map(_.as(ExitCode.Success))
  }
}